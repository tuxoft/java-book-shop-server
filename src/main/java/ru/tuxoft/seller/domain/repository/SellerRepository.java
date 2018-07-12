package ru.tuxoft.seller.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.seller.domain.SellerVO;

import java.util.List;


public interface SellerRepository extends JpaRepository<SellerVO, Long> {

    SellerVO findByUserId(String userId);

    @Query("select count(*) from SellerVO s where lower(s.lastName) like %:query%")
    Integer findCountByNameLike(@Param("query") String query);

    @Query("select s from SellerVO s where lower(s.lastName) like %:query% order by s.lastName asc")
    List<SellerVO> findByNameLike(@Param("query") String query, Pageable pageable);
}
