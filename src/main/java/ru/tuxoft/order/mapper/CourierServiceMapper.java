package ru.tuxoft.order.mapper;

import org.mapstruct.Mapper;
import ru.tuxoft.order.domain.CourierServiceVO;
import ru.tuxoft.order.dto.CourierServiceDto;

@Mapper(componentModel = "spring")
public interface CourierServiceMapper {

    CourierServiceVO courierServiceDtoToCourierServiceVO(CourierServiceDto dto);

    CourierServiceDto courierServiceVOToCourierServiceDto(CourierServiceVO vo);

}
