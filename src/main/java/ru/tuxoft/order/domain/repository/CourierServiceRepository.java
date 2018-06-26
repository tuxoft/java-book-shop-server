package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.order.domain.CourierServiceVO;
import ru.tuxoft.order.dto.CourierServiceDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CourierServiceRepository extends JpaRepository<CourierServiceVO, Long> {

    List<CourierServiceVO> findByShopCityIdAndDeletedIsFalse(Long cityId);

    @Query("select cs.sendPrice from CourierServiceVO cs where cs.id = :id")
    Optional<BigDecimal> findSendPriceById(@Param("id") Long sendOrgId);
}
