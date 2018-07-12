package ru.tuxoft.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.tuxoft.keycloak.KeycloakService;
import ru.tuxoft.profile.domain.ProfileVO;
import ru.tuxoft.profile.domain.repository.ProfileRepository;
import ru.tuxoft.profile.dto.ProfileDto;
import ru.tuxoft.profile.mapper.ProfileMapper;
import ru.tuxoft.secure.Principal;


@Service
@Slf4j
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileMapper profileMapper;

    @Autowired
    KeycloakService keycloakService;



    public ProfileDto getProfileByUserId(String userId) {
        ProfileVO profile = profileRepository.findByUserId(userId);
        if(profile == null){
            Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            profile = new ProfileVO();
            profile.setUserId(principal.getUserId());
            profile.setFirstName(principal.getFirstName());
            profile.setLastName(principal.getLastName());
            profile.setEmail(principal.getEmail());
            profile = profileRepository.saveAndFlush(profile);
        }
        return profileMapper.profileVOToProfileDto(profile);
    }

    public ProfileDto saveProfile(ProfileDto profile) {
        ProfileVO newProfileVo = profileMapper.profileDtoToProfileVO(profile);
        newProfileVo = profileRepository.saveAndFlush(newProfileVo);
        keycloakService.updateProfileInKeycloak(newProfileVo);
        return profileMapper.profileVOToProfileDto(newProfileVo);
    }


}
