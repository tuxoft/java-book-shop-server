package ru.tuxoft.profile.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.profile.domain.ProfileVO;

public interface ProfileRepository extends JpaRepository<ProfileVO,Long> {

    ProfileVO findByUserId(String userId);

}
