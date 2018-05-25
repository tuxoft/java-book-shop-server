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

}
