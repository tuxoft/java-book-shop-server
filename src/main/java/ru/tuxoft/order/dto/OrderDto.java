package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private ShopCityDto shopCity;

    private String firstName ="";

    private String middleName ="";

    private String lastName ="";

    private String email ="";

    private String phoneNumber ="";

    private String paymentMethod ="";

    private String comment ="";

    private String sendType ="";

    private Long sendOrgId;

    private Boolean isAge18 = false;

    private Boolean isTakeStatusSMS = false;

    private Boolean isTakeStatusEmail = false;

    private AddressDto addr;

    private BigDecimal totalCost;

    private BigDecimal discount;

    private BigDecimal toPay;

    private BigDecimal payFor;

    private BigDecimal sendPrice;

    private String status ="EMPTY";

    private List<OrderItemDto> orderItemList;
}
