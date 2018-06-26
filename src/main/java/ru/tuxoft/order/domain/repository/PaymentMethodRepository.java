package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.order.domain.PaymentMethodVO;
import ru.tuxoft.order.enums.SendTypeEnum;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodVO, Long> {

    List<PaymentMethodVO> findBySendTypeAndSendOrgId(SendTypeEnum value, Long sendOrgId);
}
