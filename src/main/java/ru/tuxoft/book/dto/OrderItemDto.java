package ru.tuxoft.book.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long id;

    private BookDto book;

    private Integer count;

}
