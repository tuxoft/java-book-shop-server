package ru.tuxoft.book.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.CategoryVO;
import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<CategoryVO,Long> {

    List<CategoryVO> findByIdIn(List<Long> categoryList);

    List<CategoryVO> findByParentId(Long parentId);

    @Query("select c.id from CategoryVO c where c.parentId=(:parentId)")
    List<Long> findIdByParentId(@Param("parentId") Long id);

    @Query("select distinct c.bookVOList from CategoryVO c where c.id in (:idList)")
    List<BookVO> findBookVOListByIdIn(@Param("idList") List<Long> idList, Pageable pageable);
}
