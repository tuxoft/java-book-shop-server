package ru.tuxoft.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.profile.domain.ProfileVO;
import ru.tuxoft.profile.dto.ProfileDto;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto profileVOToProfileDto(ProfileVO vo);

    @Mappings({
            @Mapping(target = "deleted", ignore = true),
    })
    ProfileVO profileDtoToProfileVO(ProfileDto dto);

}
