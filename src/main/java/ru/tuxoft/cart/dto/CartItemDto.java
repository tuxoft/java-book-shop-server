package ru.tuxoft.cart.dto;

import lombok.Data;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.cart.domain.CartItemVO;

@Data
public class CartItemDto {

    private BookDto book;

    private Integer count;

    public CartItemDto(CartItemVO cartItemVO) {
        this.book = new BookDto(cartItemVO.getBook());
        this.count = cartItemVO.getCount();
    }
}
