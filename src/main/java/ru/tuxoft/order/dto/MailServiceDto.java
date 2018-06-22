package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MailServiceDto {

    private Long id;

    private String name;

    private Long shopCityId;

    private String iconUrl;

    private BigDecimal sendPrice;

    private BigDecimal sendPriceCost;

    private BigDecimal sendPriceCommission;

    private String comment;

}
