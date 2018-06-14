package ru.tuxoft.content.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.content.domain.PromoPictureVO;

import java.util.Collection;
import java.util.List;

public interface PromoPictureRepository extends JpaRepository<PromoPictureVO, Long> {

    List<PromoPictureVO> findByDeletedIsFalse();

    @Query("select count(*) from PromoPictureVO pp where lower(pp.url) like %:query%")
    Integer findCountByUrlLike(@Param("query") String query);

    @Query("select pp from PromoPictureVO pp where lower(pp.url) like %:query% order by pp.url asc")
    List<PromoPictureVO> findByUrlLike(@Param("query") String query, Pageable pageable);

    List<PromoPictureVO> findByDeletedIsFalseAndActiveIsTrue();
}
