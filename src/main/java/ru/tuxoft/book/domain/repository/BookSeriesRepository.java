package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.AuthorVO;
import ru.tuxoft.book.domain.BookSeriesVO;

import java.util.List;

/**
 * Created by Valera on 22.05.2018.
 */
public interface BookSeriesRepository extends JpaRepository<BookSeriesVO, Long> {

    @Query("select count(*) from BookSeriesVO bs join bs.publisher p where bs.name like %:query% and p.id = :publisherId")
    Integer findCountByNameLikeAndPublisherId(@Param("query") String query, @Param("publisherId") Long publisherId);

    @Query("select bs from BookSeriesVO bs join bs.publisher p where bs.name like %:query% and p.id = :publisherId order by bs.name asc")
    List<BookSeriesVO> findByNameLikeAndPublisherId(@Param("query") String query, @Param("publisherId") Long publisherId, Pageable pageable);

}
