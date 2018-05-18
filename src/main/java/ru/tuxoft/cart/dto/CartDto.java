package ru.tuxoft.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.cart.domain.CartItemVO;
import ru.tuxoft.cart.domain.CartVO;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CartDto {

    private String id;

    private List<CartItemDto> cartItemList;

    public CartDto(CartVO cartVO) {
        this.id = cartVO.getUserId();
        if (cartVO.getCartItemVOList() != null) {
            cartItemList = new ArrayList<>();
            for (CartItemVO cartItemVO: cartVO.getCartItemVOList()) {
                cartItemList.add(new CartItemDto(cartItemVO));
            }
        }

    }
}
