package ru.tuxoft.content.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.content.domain.CategoryCarouselVO;

import java.util.List;

public interface CategoryCarouselRepository extends JpaRepository<CategoryCarouselVO, Long> {

    List<CategoryCarouselVO> findByDeletedIsFalseAndActiveIsTrue();

    @Query("select count(*) from CategoryCarouselVO cc join cc.category c where lower(c.name) like %:query%")
    Integer findCountByCategoryNameLike(@Param("query") String query);

    @Query("select cc from CategoryCarouselVO cc join cc.category c where lower(c.name) like %:query% order by c.name asc")
    List<CategoryCarouselVO> findByCategoryNameLike(@Param("query") String query, Pageable pageable);
}
