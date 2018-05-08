package ru.tuxoft.cart.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tuxoft.cart.domain.CartVO;

import java.util.List;

public interface CartRepository extends JpaRepository<CartVO, Long> {


    CartVO findByUserId(String userId);
}
