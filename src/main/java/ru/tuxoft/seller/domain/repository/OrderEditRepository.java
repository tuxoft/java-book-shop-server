package ru.tuxoft.seller.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.seller.domain.OrderEditVO;
import ru.tuxoft.seller.domain.enums.WorkStatusEnum;
import ru.tuxoft.seller.dto.OrderEditDto;

import java.util.List;

public interface OrderEditRepository extends JpaRepository<OrderEditVO, Long> {

    @Query("select o from OrderEditVO o join o.orderWorkInfo oo where oo.workStatus = :workStatus")
    List<OrderEditVO> findByWorkStatus(@Param("workStatus") WorkStatusEnum workStatusEnum, Pageable pageable);
}
