package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.order.domain.PickupPointVO;
import ru.tuxoft.order.dto.PickupPointDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PickupPointRepository extends JpaRepository<PickupPointVO,Long> {

    List<PickupPointVO> findByShopCityIdAndDeletedIsFalse(Long shopCityId);

    List<PickupPointVO> findByDeletedIsFalse();

    @Query("select pp.sendPrice from PickupPointVO pp where pp.id = :id")
    Optional<BigDecimal> findSendPriceById(@Param("id") Long sendOrgId);

}
