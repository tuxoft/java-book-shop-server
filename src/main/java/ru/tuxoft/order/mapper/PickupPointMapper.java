package ru.tuxoft.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.order.domain.PickupPointVO;
import ru.tuxoft.order.dto.PickupPointDto;

@Mapper(componentModel = "spring", uses = {BookMapper.class, PickupPointResolver.class})
public interface PickupPointMapper {

    @Mappings({
            @Mapping(target = "coords", expression = "java( java.util.Arrays.asList(vo.getCoordX(), vo.getCoordY()) )"),
            @Mapping(target = "addr", ignore = true)
    })
    PickupPointDto pickupPointVOToPickupPointDto(PickupPointVO vo);

    @Mappings({
            @Mapping(target = "addrCity", source = "addr.city"),
            @Mapping(target = "addrStreet", source = "addr.street"),
            @Mapping(target = "addrHouse", source = "addr.house"),
            @Mapping(target = "addrHousing", source = "addr.housing"),
            @Mapping(target = "addrBuilding", source = "addr.building"),
            @Mapping(target = "addrRoom", source = "addr.room")
    })
    PickupPointVO pickupPointDtoToPickupPointVO(PickupPointDto dto);

}
