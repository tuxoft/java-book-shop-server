package ru.tuxoft.cart.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.cart.domain.CartItemVO;

/**
 * Created by Valera on 08.05.2018.
 */
public interface CartItemRepository extends JpaRepository<CartItemVO, Long>{
}
