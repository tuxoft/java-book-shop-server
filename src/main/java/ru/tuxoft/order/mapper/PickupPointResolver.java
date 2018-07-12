package ru.tuxoft.order.mapper;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;
import ru.tuxoft.order.domain.PickupPointVO;
import ru.tuxoft.order.dto.AddressDto;
import ru.tuxoft.order.dto.PickupPointDto;

@Component
public class PickupPointResolver {

    @ObjectFactory
    public PickupPointDto resolve(PickupPointVO pickupVO, @TargetType Class<PickupPointDto> type) {
        PickupPointDto pickupPointDto = new PickupPointDto();
        AddressDto addressDto = new AddressDto();
        addressDto.setIndex(pickupVO.getAddrIndex());
        addressDto.setCity(pickupVO.getAddrCity());
        addressDto.setStreet(pickupVO.getAddrStreet());
        addressDto.setHouse(pickupVO.getAddrHouse());
        addressDto.setHousing(pickupVO.getAddrHousing());
        addressDto.setBuilding(pickupVO.getAddrBuilding());
        addressDto.setRoom(pickupVO.getAddrRoom());
        pickupPointDto.setAddr(addressDto);

        return pickupPointDto;
    }
}
