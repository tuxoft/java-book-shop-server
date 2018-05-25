package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.AuthorVO;

import java.util.List;
import java.util.LongSummaryStatistics;

public interface AuthorRepository extends JpaRepository<AuthorVO, Long> {

    @Query("select count(*) from AuthorVO a where lower(a.lastName) like %:query%")
    Integer findCountByNameLike(@Param("query") String query);

    @Query("select a from AuthorVO a where lower(a.lastName) like %:query% order by a.lastName asc")
    List<AuthorVO> findByNameLike(@Param("query") String query, Pageable pageable);
}
