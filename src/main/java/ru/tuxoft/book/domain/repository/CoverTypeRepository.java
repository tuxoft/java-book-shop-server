package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.tuxoft.book.domain.CoverTypeVO;

import java.util.List;

public interface CoverTypeRepository extends JpaRepository<CoverTypeVO,Long> {

    @Query("select count(*) from CoverTypeVO ct where lower(ct.name) like %:query%")
    Integer findCountByNameLike(@Param("query") String query);

    @Query("select ct from CoverTypeVO ct where lower(ct.name) like %:query% order by ct.name asc")
    List<CoverTypeVO> findByNameLike(@Param("query") String query, Pageable pageable);
}
