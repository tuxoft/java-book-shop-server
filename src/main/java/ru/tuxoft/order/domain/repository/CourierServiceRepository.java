package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.order.domain.CourierServiceVO;
import ru.tuxoft.order.dto.CourierServiceDto;

import java.util.List;

public interface CourierServiceRepository extends JpaRepository<CourierServiceVO, Long> {

    List<CourierServiceVO> findByShopCityIdAndDeletedIsFalse(Long cityId);
}
