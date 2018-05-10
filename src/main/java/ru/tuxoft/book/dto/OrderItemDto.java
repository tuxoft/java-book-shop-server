package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {

    private BookDto book;

    private Integer count;

}
