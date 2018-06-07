package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.CityVO;

import java.util.List;

public interface CityRepository extends JpaRepository<CityVO,Long> {

    @Query("select count(*) from CityVO c where lower(c.name) like %:query%")
    Integer findCountByNameLike(@Param("query") String query);

    @Query("select c from CityVO c where lower(c.name) like %:query% order by c.name asc")
    List<CityVO> findByNameLike(@Param("query") String query, Pageable pageable);

    List<CityVO> findByIdIn(List<Long> idList);
}