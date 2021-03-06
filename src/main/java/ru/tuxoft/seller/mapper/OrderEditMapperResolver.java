package ru.tuxoft.seller.mapper;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tuxoft.order.domain.*;
import ru.tuxoft.order.domain.repository.CourierServiceRepository;
import ru.tuxoft.order.domain.repository.MailServiceRepository;
import ru.tuxoft.order.domain.repository.PickupPointRepository;
import ru.tuxoft.order.dto.AddressDto;
import ru.tuxoft.order.dto.OrderDto;
import ru.tuxoft.order.dto.ShopCityDto;
import ru.tuxoft.order.enums.SendTypeEnum;
import ru.tuxoft.seller.domain.OrderEditVO;
import ru.tuxoft.seller.dto.OrderEditDto;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class OrderEditMapperResolver {

    @Autowired
    private CourierServiceRepository courierServiceRepository;

    @Autowired
    private MailServiceRepository mailServiceRepository;

    @Autowired
    private PickupPointRepository pickupPointRepository;

    @ObjectFactory
    public OrderEditDto resolve(OrderEditVO orderVO, @TargetType Class<OrderEditDto> type) {
        OrderEditDto orderDto = new OrderEditDto();
        orderDto.setPaymentMethodText(orderVO.getPaymentMethod().getText());
        AddressDto addressDto = new AddressDto();
        addressDto.setIndex(orderVO.getAddrIndex());
        addressDto.setCity(orderVO.getAddrCity());
        addressDto.setStreet(orderVO.getAddrStreet());
        addressDto.setHouse(orderVO.getAddrHouse());
        addressDto.setHousing(orderVO.getAddrHousing());
        addressDto.setBuilding(orderVO.getAddrBuilding());
        addressDto.setRoom(orderVO.getAddrRoom());
        orderDto.setAddr(addressDto);


        if (orderVO.getSendType().equals(SendTypeEnum.COURIER_SERVICE)) {
            Optional<CourierServiceVO> sendOrgOptional = courierServiceRepository.findById(orderVO.getSendOrgId());
            if (sendOrgOptional.isPresent()) {
                orderDto.setSendPrice(sendOrgOptional.get().getSendPrice());
                orderDto.setSendOrgName(sendOrgOptional.get().getName());
            }
        }
        if (orderVO.getSendType().equals(SendTypeEnum.PICKUP_POINT)) {
            Optional<PickupPointVO> sendOrgOptional  = pickupPointRepository.findById(orderVO.getSendOrgId());
            if (sendOrgOptional.isPresent()) {
                orderDto.setSendPrice(sendOrgOptional.get().getSendPrice());
                orderDto.setSendOrgName(sendOrgOptional.get().getName());
            }
        }
        if (orderVO.getSendType().equals(SendTypeEnum.MAIL_SERVICE)) {
            Optional<MailServiceVO> mailServiceOptional = mailServiceRepository.findById(orderVO.getSendOrgId());
            if (mailServiceOptional.isPresent()) {
                BigDecimal sendPrice = mailServiceOptional.get().getSendPriceCost();
                sendPrice = sendPrice.add(mailServiceOptional.get().getSendPriceCost().multiply(mailServiceOptional.get().getSendPriceCommission()));
                orderDto.setSendPrice(sendPrice);
                orderDto.setSendOrgName(mailServiceOptional.get().getName());
            }
        }

        return orderDto;
    }

}
