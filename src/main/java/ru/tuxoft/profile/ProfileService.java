package ru.tuxoft.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tuxoft.order.dto.OrderDto;
import ru.tuxoft.profile.domain.ProfileVO;
import ru.tuxoft.profile.domain.repository.ProfileRepository;
import ru.tuxoft.profile.dto.ProfileDto;
import ru.tuxoft.profile.mapper.ProfileMapper;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileMapper profileMapper;

    public ProfileDto getProfileByUserId(String userId) {
        return profileMapper.profileVOToProfileDto(profileRepository.findByUserId(userId));
    }
}
