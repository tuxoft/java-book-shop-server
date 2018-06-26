package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentMethodDto {

    private String value;

    private String name;

    private String comment;

}
