package ru.tuxoft.book.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BasketDto {

    private Long id;

    private List<BasketItemDto> basketItemList;
}
