package ru.tuxoft.book.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private BookDto book;

    private Integer count;

}
