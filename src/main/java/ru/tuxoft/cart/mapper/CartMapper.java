package ru.tuxoft.cart.mapper;

import org.mapstruct.Mapper;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.cart.domain.CartVO;
import ru.tuxoft.cart.dto.CartDto;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface CartMapper {

    CartDto cartVOToCartDto(CartVO vo);

    CartVO cartDtoToCartVO(CartDto dto);

}
