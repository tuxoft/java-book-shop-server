package ru.tuxoft.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.order.domain.MailServiceVO;
import ru.tuxoft.order.dto.MailServiceDto;

@Mapper(componentModel = "spring")
public interface MailServiceMapper {

    MailServiceVO mailServiceDtoToMailServiceVO(MailServiceDto dto);

    @Mappings({
            @Mapping(target = "sendPrice", expression = "java( vo.getSendPriceCost().add(vo.getSendPriceCost().multiply(vo.getSendPriceCommission())) )"),
            @Mapping(target = "comment", constant = "Стоимость доставки и комиссия за наложенный платёж указана ориентировочно. После комплектации заказа, при наличии АВИА-режима, с вами свяжется наш специалист для согласования стоимости доставки. Почтовые отправления хранятся в отделениях почтовой связи 1 месяц. За хранение почтовых отправлений свыше 5 дней взыскивается плата за каждые сутки. В случае невостребованности или отказа от оплаты заказ возвращается отправителю."),
            @Mapping(target = "sendPriceCommission", expression = "java( vo.getSendPriceCost().multiply(vo.getSendPriceCommission()) )")
    })
    MailServiceDto mailServiceVOToMailServiceDto(MailServiceVO vo);

}
