package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.tuxoft.book.domain.OrderItemVO;

public interface OrderItemRepository extends JpaRepository<OrderItemVO,Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from order_items oi where oi.book_id = :bookId", nativeQuery = true)
    void deleteByBookId(@Param("bookId") Long bookId);
}
