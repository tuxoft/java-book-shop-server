package ru.tuxoft.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.order.domain.ShopCityVO;
import ru.tuxoft.order.dto.ShopCityDto;

@Mapper(componentModel = "spring", uses = {OrderMapperResolver.class} )
public interface ShopCityMapper {

    @Mappings({
            @Mapping(target = "coords", expression = "java( java.util.Arrays.asList(vo.getCoordX(), vo.getCoordY()) )")
    })
    ShopCityDto shopCityVOToShopCityDto(ShopCityVO vo);

    @Mappings({
            @Mapping(target = "coordX", ignore = true),
            @Mapping(target = "coordY", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    ShopCityVO shopCityDtoToShopCityVO(ShopCityDto dto);
}
