package ru.tuxoft.seller.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.seller.domain.OrderWorkInfo;

public interface OrderWorkInfoRepository extends JpaRepository<OrderWorkInfo, Long> {

}
