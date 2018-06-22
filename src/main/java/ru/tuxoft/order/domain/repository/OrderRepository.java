package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.order.domain.OrderVO;

public interface OrderRepository extends JpaRepository<OrderVO,Long> {

    OrderVO findByIdAndUserId(Long orderId, String userId);
}
