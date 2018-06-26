package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.order.domain.MailServiceVO;

import java.math.BigDecimal;
import java.util.List;

public interface MailServiceRepository extends JpaRepository<MailServiceVO, Long> {

    List<MailServiceVO> findByShopCityIdAndDeletedIsFalse(Long cityId);


}
