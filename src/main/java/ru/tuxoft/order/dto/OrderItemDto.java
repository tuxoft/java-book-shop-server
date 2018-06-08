package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.cart.dto.CartItemDto;

@Data
@NoArgsConstructor
public class OrderItemDto {

    private BookDto book;

    private Integer count;

    public OrderItemDto(CartItemDto cto) {
        this.book = cto.getBook();
        this.count = cto.getCount();
    };
}
