package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.order.domain.PickupPointVO;
import ru.tuxoft.order.dto.PickupPointDto;

import java.util.List;

public interface PickupPointRepository extends JpaRepository<PickupPointVO,Long> {

    List<PickupPointVO> findByShopCityIdAndDeletedIsFalse(Long shopCityId);

    List<PickupPointVO> findByDeletedIsFalse();

}
