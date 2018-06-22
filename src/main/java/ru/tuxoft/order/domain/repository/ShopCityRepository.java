package ru.tuxoft.order.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.order.domain.ShopCityVO;

import java.util.List;

public interface ShopCityRepository extends JpaRepository<ShopCityVO,Long> {

    @Query("select count(*) from ShopCityVO sc where lower(sc.name) like %:query%")
    Integer findCountByNameLike(@Param("query") String query);

    @Query("select sc from ShopCityVO sc where lower(sc.name) like %:query% order by sc.name asc")
    List<ShopCityVO> findByNameLike(@Param("query") String query, Pageable pageable);

    List<ShopCityVO> findByIdIn(List<Long> idList);
}
