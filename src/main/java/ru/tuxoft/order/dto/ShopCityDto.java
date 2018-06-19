package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.cart.dto.CartItemDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ShopCityDto {

    private Long id;

    private List<Double> coords;

    private String name;

}
