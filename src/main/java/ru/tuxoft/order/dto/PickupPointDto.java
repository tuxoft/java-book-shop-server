package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PickupPointDto {

    private Long orgId;

    private List<Double> coords;

    private String orgName;

    private String orgWorkPeriod;

    private String orgIconUrl = "http://placehold.it/85x22";

    private String orgAddr;

    private String payCase;

}
