package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.LanguageVO;

import java.util.List;

public interface LanguageRepository extends JpaRepository<LanguageVO,Long> {

    @Query("select count(*) from LanguageVO l where lower(l.name) like %:query%")
    Integer findCountByNameLike(@Param("query") String query);

    @Query("select l from LanguageVO l where lower(l.name) like %:query% order by l.name asc")
    List<LanguageVO> findByNameLike(@Param("query") String query, Pageable pageable);

    List<LanguageVO> findByIdIn(List<Long> idList);
}
