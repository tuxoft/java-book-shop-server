package ru.tuxoft.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tuxoft.profile.dto.ProfileDto;

@RestController
@Slf4j
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET, path = "/profile")
    public ProfileDto getProfile(
    ) {
        return profileService.getProfileByUserId(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/profile", produces = "application/json")
    public ProfileDto updateOrder(
            @RequestBody ProfileDto profileDto
    ) {
        return profileService.saveProfile(profileDto);
    }

}
