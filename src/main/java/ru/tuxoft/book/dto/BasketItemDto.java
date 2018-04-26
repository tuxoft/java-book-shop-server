package ru.tuxoft.book.dto;

import lombok.Data;

@Data
public class BasketItemDto {

    private BookDto book;

    private Integer count;
}
