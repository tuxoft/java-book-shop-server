package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CourierServiceDto {

    private Long id;

    private String name;

    private Long shopCityId;

    private String iconUrl;

    private String payCase;

    private BigDecimal sendPrice;

    private Integer maxWeight;

}
