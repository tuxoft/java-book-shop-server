package ru.tuxoft.book.dto;

import lombok.Data;

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

    private String discount;

    private String status;

    private List<OrderItemDto> orderItemList;
}
