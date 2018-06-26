package ru.tuxoft.cart.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.tuxoft.cart.domain.CartItemVO;

/**
 * Created by Valera on 08.05.2018.
 */
public interface CartItemRepository extends JpaRepository<CartItemVO, Long>{

    @Modifying
    @Transactional
    @Query(value = "delete from cart_items ci where ci.book_id = :bookId", nativeQuery = true)
    void deleteByBookId(@Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query(value = "delete from cart_items ci where ci.cart_id = :cartId", nativeQuery = true)
    void deleteByCartId(@Param("cartId") Long cartId);
}
