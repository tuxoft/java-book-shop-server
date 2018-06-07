package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.AgeLimitVO;

import java.util.List;

public interface AgeLimitRepository extends JpaRepository<AgeLimitVO,Long> {

    @Query("select count(*) from AgeLimitVO a where lower(a.name) like %:query%")
    Integer findCountByNameLike(@Param("query") String query);

    @Query("select a from AgeLimitVO a where lower(a.name) like %:query% order by a.name asc")
    List<AgeLimitVO> findByNameLike(@Param("query") String query, Pageable pageable);

    List<AgeLimitVO> findByIdIn(List<Long> idList);
}
