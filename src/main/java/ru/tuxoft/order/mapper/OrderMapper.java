package ru.tuxoft.order.mapper;

import org.mapstruct.Mapper;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.order.domain.OrderVO;
import ru.tuxoft.order.dto.OrderDto;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface OrderMapper {

    OrderDto orderVOToOrderDto(OrderVO vo);

    OrderVO orderDtoToOrderVO(OrderDto dto);

}
