package ru.tuxoft.content.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.content.domain.PromoPictureVO;

import java.util.List;

public interface PromoPictureRepository extends JpaRepository<PromoPictureVO, Long> {

    List<PromoPictureVO> findByDeletedIsFalse();

}
