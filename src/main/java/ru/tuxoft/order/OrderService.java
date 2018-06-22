package ru.tuxoft.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.cart.CartService;
import ru.tuxoft.order.domain.MailServiceVO;
import ru.tuxoft.order.domain.OrderVO;
import ru.tuxoft.order.domain.PaymentMethodVO;
import ru.tuxoft.order.domain.repository.*;
import ru.tuxoft.order.dto.*;
import ru.tuxoft.order.enums.DeliveryServiceTypeEnum;
import ru.tuxoft.order.enums.PaymentMethodEnum;
import ru.tuxoft.order.enums.SendTypeEnum;
import ru.tuxoft.order.mapper.*;
import ru.tuxoft.profile.domain.ProfileVO;
import ru.tuxoft.profile.domain.repository.ProfileRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        templateOrder.getOrderItemList().addAll(cartService.getCart(userId).getCartItemList()
                .stream().map(e -> new OrderItemDto(e)).collect(Collectors.toList()));
    }

    public OrderDto getOrderById(String userId, Long orderId) throws IllegalArgumentException {
        /*OrderDto od = new OrderDto();
        od.setOrderItemList(new ArrayList<OrderItemDto>());
        od.getOrderItemList().addAll(cartService.getCart(userId).getCartItemList()
                .stream().map(e -> new OrderItemDto(e)).collect(Collectors.toList()));
        od.setDiscount(new BigDecimal(0));
        od.setPayFor(new BigDecimal(100));
        od.setToPay(new BigDecimal(150));
        od.setTotalCost(new BigDecimal(200));
        od.setSendPrice(new BigDecimal(50));
        od.setAddr(new AddressDto());
        od.setStatus("FILLED");
        log.info("order", od.getTotalCost());*/
        OrderVO order = orderRepository.findByIdAndUserId(orderId, userId);
        if (order != null) {
            return orderMapper.orderVOToOrderDto(order);
        } else {
            throw new IllegalArgumentException("Ошибка запроса ордера. Ордера с указанным id  для указанного пользователя в БД не обнаружено");
        }
    }

    public String updateOrder(OrderDto orderDto) {
        String redirectUrl = "/pay";
        OrderVO orderVO = orderMapper.orderDtoToOrderVO(orderDto);
        orderRepository.saveAndFlush(orderVO);
        return redirectUrl;
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
        for (DeliveryServiceTypeEnum deliveryServiceTypeEnum: DeliveryServiceTypeEnum.values()) {
            if (deliveryServiceTypeEnum.getValue().equals(sendType)) {
                List<PaymentMethodDto> result = new ArrayList<>();
                List<PaymentMethodVO> paymentMethodVOList = paymentMethodRepository.findByDeliveryServiceTypeAndDeliveryServiceId(deliveryServiceTypeEnum.getValue(),sendOrgId);
                for (PaymentMethodVO paymentMethodVO: paymentMethodVOList) {
                    for (PaymentMethodEnum paymentMethodEnum: PaymentMethodEnum.values()) {
                        if (paymentMethodEnum.getValue().equals(paymentMethodVO.getPaymentMethod())) {
                            result.add(new PaymentMethodDto() {{setName(paymentMethodEnum.getText()); setComment(paymentMethodEnum.getComment());}});
                            break;
                        }
                    }
                }
                return result;
            }
        }
        return null;
    }
}
