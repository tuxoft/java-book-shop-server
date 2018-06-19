package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.order.domain.ShopCityVO;

public interface ShopCityRepository extends JpaRepository<ShopCityVO,Long> {
}
