package ru.tuxoft.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.order.domain.OrderVO;
import ru.tuxoft.order.dto.OrderDto;
import ru.tuxoft.order.enums.PaymentMethodEnum;
import ru.tuxoft.order.enums.SendTypeEnum;
import ru.tuxoft.order.enums.StatusEnum;

@Mapper(componentModel = "spring", uses = {OrderMapperResolver.class, ShopCityMapper.class})
public interface OrderMapper {

    OrderDto orderVOToOrderDto(OrderVO vo);

    @Mappings({
            @Mapping(target = "deleted", ignore = true ),
            @Mapping(target = "userId", expression = "java( org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName() )"),
            @Mapping(target = "addrIndex", source = "addr.index"),
            @Mapping(target = "addrCity", source = "addr.city"),
            @Mapping(target = "addrStreet", source = "addr.street"),
            @Mapping(target = "addrHouse", source = "addr.house"),
            @Mapping(target = "addrHousing", source = "addr.housing"),
            @Mapping(target = "addrBuilding", source = "addr.building"),
            @Mapping(target = "addrRoom", source = "addr.room")
    })
    OrderVO orderDtoToOrderVO(OrderDto dto);

    default PaymentMethodEnum paymentMethodStringToPaymentMethodEnum(String paymentMethodString) {
        for (PaymentMethodEnum paymentMethodEnum: PaymentMethodEnum.values()) {
            if (paymentMethodEnum.getValue().equals(paymentMethodString)) {
                return paymentMethodEnum;
            }
        }
        return null;
    };

    default String paymentMethodEnumToPaymentMethodString(PaymentMethodEnum paymentMethodEnum) {
        return paymentMethodEnum.getValue();
    };

    default SendTypeEnum sendTypeStringToSendTypeEnum(String sendTypeString) {
        for (SendTypeEnum sendTypeEnum: SendTypeEnum.values()) {
            if (sendTypeEnum.getValue().equals(sendTypeString)) {
                return sendTypeEnum;
            }
        }
        return null;
    };

    default String sendTypeEnumToSendTypeString(SendTypeEnum sendTypeEnum) {
        return sendTypeEnum.getValue();
    };

    default StatusEnum statusStringToStatusEnum(String statusString) {
        for (StatusEnum statusEnum: StatusEnum.values()) {
            if (statusEnum.getValue().equals(statusString)) {
                return statusEnum;
            }
        }
        return null;
    };

    default String statusEnumToStatusString(StatusEnum statusEnum) {
        return statusEnum.getValue();
    };

}
