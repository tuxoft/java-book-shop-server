package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.BookVO;


import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<BookVO, Long> {

    @Query("select distinct b from BookVO b join b.categories c where c.id in (:idList)")
    List<BookVO> findBookByCategoryIdIn(@Param("idList") List<Long> idList, Pageable pageable);

    @Query("select count(distinct b) from BookVO b join b.categories c where c.id in (:idList)")
    Integer countBookByCategoryIdIn(@Param("idList") List<Long> idList);

    @Query("select b from BookVO b join b.publisher p where p.id = (:publisherId)")
    List<BookVO> findBookByPublisherId(@Param("publisherId") Long publisherId);

    @Query("select b from BookVO b join b.bookSeries bs where bs.id = (:bookSeriesId)")
    List<BookVO> findBookByBookSeriesId(@Param("bookSeriesId") Long bookSeriesId);

    @Query("select b from BookVO b join b.city c where c.id = (:cityId)")
    List<BookVO> findBookByCityId(@Param("cityId") Long cityId);

    @Query("select b from BookVO b join b.language l where l.id = (:languageId)")
    List<BookVO> findBookByLanguageId(@Param("languageId") Long languageId);

    @Query("select b.price from BookVO b where b.id = (:id)")
    BigDecimal findPriceById(@Param("id") Long id);

}
