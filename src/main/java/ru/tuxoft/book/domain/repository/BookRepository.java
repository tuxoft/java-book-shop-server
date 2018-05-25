package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.BookVO;


import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<BookVO, Long> {

    @Query("select distinct b from BookVO b join b.categories c where c.id in (:idList)")
    List<BookVO> findBookByCategoryIdIn(@Param("idList") List<Long> idList, Pageable pageable);

    @Query("select count(distinct b) from BookVO b join b.categories c where c.id in (:idList)")
    Integer countBookByCategoryIdIn(@Param("idList") List<Long> idList);
}
