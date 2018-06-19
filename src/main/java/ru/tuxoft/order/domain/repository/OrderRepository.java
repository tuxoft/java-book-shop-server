package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.order.domain.OrderVO;

/**
 * Created by Valera on 15.06.2018.
 */
public interface OrderRepository extends JpaRepository<OrderVO,Long> {

    OrderVO findByIdAndUserId(Long orderId, String userId);
}
