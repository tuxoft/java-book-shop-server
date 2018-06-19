package ru.tuxoft.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.order.domain.PickupPointVO;
import ru.tuxoft.order.dto.PickupPointDto;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface PickupPointMapper {

    @Mappings({
            @Mapping(target = "coords", expression = "java( java.util.Arrays.asList(vo.getCoordX(), vo.getCoordY()) )")
    })
    PickupPointDto pickupPointVOToPickupPointDto(PickupPointVO vo);

    PickupPointVO pickupPointDtoToPickupPointVO(PickupPointDto dto);

}
