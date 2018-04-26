package ru.tuxoft.book.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String shippingAddress;

    private String paymentMethod;

    private BigDecimal totalCost;

    private BigDecimal discount;

    private BigDecimal toPay;

    private BigDecimal payFor;

    private String status;

    private List<OrderItemDto> orderItemList;
}
