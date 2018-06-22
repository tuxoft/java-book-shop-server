package ru.tuxoft.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.order.domain.MailServiceVO;

import java.util.List;

public interface MailServiceRepository extends JpaRepository<MailServiceVO, Long> {

    List<MailServiceVO> findByShopCityIdAndDeletedIsFalse(Long cityId);
}
