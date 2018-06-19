package ru.tuxoft.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tuxoft.order.dto.OrderDto;
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

}
