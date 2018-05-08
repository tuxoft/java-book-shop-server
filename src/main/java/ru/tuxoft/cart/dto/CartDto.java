package ru.tuxoft.cart.dto;

import lombok.Data;
import ru.tuxoft.cart.domain.CartItemVO;
import ru.tuxoft.cart.domain.CartVO;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDto {

    private List<CartItemDto> cartItemList;

    public CartDto(CartVO cartVO) {
        if (cartVO.getCartItemVOList() != null) {
            cartItemList = new ArrayList<>();
            for (CartItemVO cartItemVO: cartVO.getCartItemVOList()) {
                cartItemList.add(new CartItemDto(cartItemVO));
            }
        }

    }
}
