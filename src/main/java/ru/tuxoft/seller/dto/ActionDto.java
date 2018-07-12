package ru.tuxoft.seller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ActionDto {

    private Long id;

    private String changeField;

    private String oldValue;

    private String newValue;

    private String description;

    private SellerDto worker;

    private Date changeDate;

}
