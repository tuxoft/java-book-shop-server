package ru.tuxoft.order.mapper;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;
import ru.tuxoft.order.domain.ShopCityVO;
import ru.tuxoft.order.dto.ShopCityDto;

@Component
public class OrderMapperResolver {

    @ObjectFactory
    public ShopCityVO resolve(ShopCityDto shopCityDto, @TargetType Class<ShopCityVO> type) {
        ShopCityVO shopCityVO = new ShopCityVO();
        if (shopCityDto.getCoords().size() == 2) {
            shopCityVO.setCoordX(shopCityDto.getCoords().get(0));
            shopCityVO.setCoordY(shopCityDto.getCoords().get(1));
        }
        return shopCityVO;
    }
}
