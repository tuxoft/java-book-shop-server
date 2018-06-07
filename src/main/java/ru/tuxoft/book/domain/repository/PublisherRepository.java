package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.PublisherVO;

import java.util.List;

public interface PublisherRepository extends JpaRepository<PublisherVO,Long> {

    @Query("select count(*) from PublisherVO p where lower(p.name) like %:query%")
    Integer findCountByNameLike(@Param("query") String query);

    @Query("select p from PublisherVO p where lower(p.name) like %:query% order by p.name asc")
    List<PublisherVO> findByNameLike(@Param("query") String query, Pageable pageable);

    List<PublisherVO> findByIdIn(List<Long> idList);
}
