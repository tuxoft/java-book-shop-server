package ru.tuxoft.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.cart.CartService;
import ru.tuxoft.cart.domain.CartItemVO;
import ru.tuxoft.cart.dto.CartDto;
import ru.tuxoft.order.domain.*;
import ru.tuxoft.order.domain.repository.*;
import ru.tuxoft.order.dto.*;
import ru.tuxoft.order.enums.PaymentMethodEnum;
import ru.tuxoft.order.enums.SendTypeEnum;
import ru.tuxoft.order.enums.StatusEnum;
import ru.tuxoft.order.mapper.*;
import ru.tuxoft.profile.domain.ProfileVO;
import ru.tuxoft.profile.domain.repository.ProfileRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ShopCityRepository shopCityRepository;

    @Autowired
    ShopCityMapper shopCityMapper;

    @Autowired
    PickupPointRepository pickupPointRepository;

    @Autowired
    PickupPointMapper pickupPointMapper;

    @Autowired
    CartService cartService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CourierServiceRepository courierServiceRepository;

    @Autowired
    CourierServiceMapper courierServiceMapper;

    @Autowired
    MailServiceRepository mailServiceRepository;

    @Autowired
    MailServiceMapper mailServiceMapper;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    public static final List<StatusEnum> ACTIVE_STATUS_LIST = Collections.unmodifiableList(Arrays.asList(StatusEnum.PAYD, StatusEnum.SHIPPING, StatusEnum.UNPAID));

    public static final List<StatusEnum> CLOSED_STATUS_LIST = Collections.unmodifiableList(Arrays.asList(StatusEnum.DELIVERY));

    public static final List<StatusEnum> CANCELED_STATUS_LIST = Collections.unmodifiableList(Arrays.asList(StatusEnum.CANCELED));

    public OrderDto getTemplateOrder(String userId) {
        OrderDto templateOrder = new OrderDto();
        setInformationFromCart(templateOrder, userId);
        setInformationFromProfile(templateOrder, userId);
        setInformationAboutPayment(templateOrder, userId);
        return templateOrder;
    }

    private void setInformationAboutPayment(OrderDto templateOrder, String userId) {
        BigDecimal bookListCost = BigDecimal.ZERO;
        for (OrderItemDto orderItem: templateOrder.getOrderItemList()) {
            bookListCost = bookListCost.add(new BigDecimal(orderItem.getCount()).multiply(orderItem.getBook().getPrice()));
        }
        templateOrder.setTotalCost(bookListCost);
        templateOrder.setDiscount(BigDecimal.ZERO);
        templateOrder.setToPay(templateOrder.getTotalCost().subtract(templateOrder.getDiscount()));
        templateOrder.setPayFor(BigDecimal.ZERO);
    }

    private void setInformationFromProfile(OrderDto templateOrder, String userId) {
        ProfileVO profile = profileRepository.findByUserId(userId);
        if(profile==null){
            profile = new ProfileVO();
        }
        templateOrder.setFirstName(profile.getFirstName());
        templateOrder.setMiddleName(profile.getMiddleName());
        templateOrder.setLastName(profile.getLastName());
        templateOrder.setEmail(profile.getEmail());
        templateOrder.setPhoneNumber(profile.getPhoneNumber());
        templateOrder.setShopCity(shopCityMapper.shopCityVOToShopCityDto(profile.getShopCity()));
        AddressDto addr = new AddressDto();
        addr.setIndex(profile.getAddrIndex());
        addr.setCity(profile.getAddrCity());
        addr.setStreet(profile.getAddrStreet());
        addr.setHouse(profile.getAddrHouse());
        addr.setHousing(profile.getAddrHousing());
        addr.setBuilding(profile.getAddrBuilding());
        addr.setRoom(profile.getAddrRoom());
        templateOrder.setAddr(addr);
    }

    private void setInformationFromCart(OrderDto templateOrder, String userId) {
        templateOrder.setOrderItemList(new ArrayList<OrderItemDto>());
        CartDto cart = cartService.getCart(userId);
        if(cart.getCartItemList()==null){
            cart.setCartItemList(new ArrayList<>());
        }
        templateOrder.getOrderItemList().addAll(cart.getCartItemList()
                .stream().map(e -> new OrderItemDto(e)).collect(Collectors.toList()));
    }

    public OrderDto getOrderById(String userId, Long orderId) throws IllegalArgumentException {
        OrderVO order = orderRepository.findByIdAndUserId(orderId, userId);
        if (order != null) {
            return orderMapper.orderVOToOrderDto(order);
        } else {
            throw new IllegalArgumentException("Ошибка запроса ордера. Ордера с указанным id  для указанного пользователя в БД не обнаружено");
        }
    }

    public String createOrder(OrderDto orderDto) {
        OrderVO orderVO = orderMapper.orderDtoToOrderVO(orderDto);
        orderVO.getOrderItemList().forEach(e -> e.setOrder(orderVO));
        String redirectUrl = "";
        if (orderVO.getPaymentMethod().equals(PaymentMethodEnum.PAYMENT_IN_SITE)) {
            redirectUrl = "/pay";
        } else if (orderVO.getPaymentMethod().equals(PaymentMethodEnum.PAYMENT_IN_RECEIVE)) {
            redirectUrl = "/home";
        } else {
            throw new IllegalArgumentException("Ошибка сохранения заказа. Неверный метод оплаты");
        }
        validateOrder(orderVO);
        cleanCart(orderVO.getUserId());
        orderRepository.saveAndFlush(orderVO);
        return redirectUrl;
    }

    private void cleanCart(String userId) {
        cartService.cleanCart(userId);
    }

    private void validateOrder(OrderVO orderVO) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemVO orderItem: orderVO.getOrderItemList()) {
            orderItem.getBook().setPrice(bookRepository.findPriceById(orderItem.getBook().getId()));
            totalCost = totalCost.add(orderItem.getBook().getPrice().multiply(new BigDecimal(orderItem.getCount())));
        }
        if (orderVO.getSendType().equals(SendTypeEnum.COURIER_SERVICE)) {
            Optional<BigDecimal> courierServiceSendPriceOptional = courierServiceRepository.findSendPriceById(orderVO.getSendOrgId());
            if (courierServiceSendPriceOptional.isPresent()) {
                totalCost = totalCost.add(courierServiceSendPriceOptional.get());
            } else {
                throw new IllegalArgumentException("Ошибка сохранения заказа. Неверный sendOrgId");
            }

        } else if (orderVO.getSendType().equals(SendTypeEnum.MAIL_SERVICE)){
            Optional<MailServiceVO> mailServiceOptional = mailServiceRepository.findById(orderVO.getSendOrgId());
            if (mailServiceOptional.isPresent()) {
                totalCost = totalCost.add(mailServiceOptional.get().getSendPriceCost());
                totalCost = totalCost.add(mailServiceOptional.get().getSendPriceCost().multiply(mailServiceOptional.get().getSendPriceCommission()));
            } else {
                throw new IllegalArgumentException("Ошибка сохранения заказа. Неверный sendOrgId");
            }

        } else if (orderVO.getSendType().equals(SendTypeEnum.PICKUP_POINT)){
            Optional<BigDecimal> pickupPointSendPriceOptional = pickupPointRepository.findSendPriceById(orderVO.getSendOrgId());
            if (pickupPointSendPriceOptional.isPresent()) {
                totalCost = totalCost.add(pickupPointSendPriceOptional.get());
            } else {
                throw new IllegalArgumentException("Ошибка сохранения заказа. Неверный sendOrgId");
            }
        }
        if (totalCost.compareTo(orderVO.getTotalCost()) != 0) {
            throw new IllegalArgumentException("Ошибка сохранения заказа. Неверный totalCost");
        }
        if (orderVO.getToPay().compareTo(totalCost) != 0) {
            throw new IllegalArgumentException("Ошибка сохранения заказа. Неверный toPay");
        }
        if (orderVO.getPayFor().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Ошибка сохранения заказа. Неверный payFor");
        }
        orderVO.setCreateDate(new Date());
    }

    public List<ShopCityDto> getShopCities() {
        return shopCityRepository.findAll().stream().map((shopCityVO) -> shopCityMapper.shopCityVOToShopCityDto(shopCityVO)).collect(Collectors.toList());

    }

    public List<PickupPointDto> getPickupPoint(Long cityId) {
        return pickupPointRepository.findByShopCityIdAndDeletedIsFalse(cityId).stream().map((pickupPointVO) -> pickupPointMapper.pickupPointVOToPickupPointDto(pickupPointVO)).collect(Collectors.toList());
    }

    public List<CourierServiceDto> getCourierService(Long cityId) {
        return courierServiceRepository.findByShopCityIdAndDeletedIsFalse(cityId).stream().map((courierServiceVO -> courierServiceMapper.courierServiceVOToCourierServiceDto(courierServiceVO))).collect(Collectors.toList());

    }

    public List<MailServiceDto> getMailService(Long cityId) {
        return mailServiceRepository.findByShopCityIdAndDeletedIsFalse(cityId).stream().map((mailServiceVO -> mailServiceMapper.mailServiceVOToMailServiceDto(mailServiceVO))).collect(Collectors.toList());
    }

    public List<PaymentMethodDto> getPaymentMethod(String sendType, Long sendOrgId) {
        for (SendTypeEnum sendTypeEnum: SendTypeEnum.values()) {
            if (sendTypeEnum.getValue().equals(sendType)) {
                List<PaymentMethodDto> result = new ArrayList<>();
                List<PaymentMethodVO> paymentMethodVOList = paymentMethodRepository.findBySendTypeAndSendOrgId(sendTypeEnum,sendOrgId);
                for (PaymentMethodVO paymentMethodVO: paymentMethodVOList) {
                    result.add(new PaymentMethodDto() {{setValue(paymentMethodVO.getPaymentMethod().getValue()); setName(paymentMethodVO.getPaymentMethod().getText()); setComment(paymentMethodVO.getPaymentMethod().getComment());}});
                }
                return result;
            }
        }
        return null;
    }

    public List<OrderDto> getOrderList(String userId, String type) {
        List<StatusEnum> statusList = new ArrayList<>();
        if (type.equals("active")) {
            statusList = ACTIVE_STATUS_LIST;
        } else if (type.equals("all")) {
            for (StatusEnum statusEnum: StatusEnum.values()) {
                statusList.add(statusEnum);
            }
        } else if (type.equals("closed")) {
            statusList = CLOSED_STATUS_LIST;
        } if (type.equals("canceled")) {
            statusList = CANCELED_STATUS_LIST;
        }
        return orderRepository.findByUserIdAndStatusIn(userId, statusList).stream().map(orderVO -> orderMapper.orderVOToOrderDto(orderVO)).collect(Collectors.toList());
    }
}
