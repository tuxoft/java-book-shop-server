package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class PickupPointDto {

    private Long id;

    private List<Double> coords;

    private String name;

    private Long shopCityId;

    private String workPeriod;

    private String iconUrl;

    private String addr;

    private String payCase;

    private BigDecimal sendPrice;

}
