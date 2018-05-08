package ru.tuxoft.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.cart.domain.CartItemVO;
import ru.tuxoft.cart.domain.CartVO;
import ru.tuxoft.cart.domain.repository.CartItemRepository;
import ru.tuxoft.cart.domain.repository.CartRepository;
import ru.tuxoft.cart.dto.CartDto;

import java.util.Optional;


@Component
@Slf4j
public class CartService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public CartDto getCart(String id) {
        return new CartDto(cartRepository.findByUserId(id));
    }

    public CartDto addOrChangeBookToCart(String userId, Long bookId, int count) throws IllegalArgumentException {
        CartVO cart = cartRepository.findByUserId(userId);
        boolean find = false;
        for (CartItemVO cartItemVO: cart.getCartItemVOList()) {
            if (cartItemVO.getBook().getId() == bookId ) {
                cartItemVO.setCount(count);
                find = true;
                break;
            }
        }
        if (!find) {
            Optional<BookVO> book = bookRepository.findById(bookId);
            if (book.isPresent()) {
                cart.getCartItemVOList().add(new CartItemVO(cart, book.get(), count));
            } else {
                throw new IllegalArgumentException("Ошибка добавления в корзину. Книги с указанным id в БД не обнаружено");
            }
        }
        cartRepository.saveAndFlush(cart);
        return new CartDto(cart);
    }

    public CartDto deleteBookToCart(String userId, Long bookId) throws IllegalArgumentException {
        CartVO cart = cartRepository.findByUserId(userId);
        int deleteIndex = -1;
        for (int i=0; i< cart.getCartItemVOList().size(); i++) {
            if (cart.getCartItemVOList().get(i).getBook().getId() == bookId ) {
                deleteIndex = i;
                break;
            }
        }
        if (deleteIndex != -1) {
            cartItemRepository.delete(cart.getCartItemVOList().remove(deleteIndex));
        } else {
            throw new IllegalArgumentException("Ошибка удаления из корзины. Книги с указанным id в корзине не обнаружено");
        }
        cartRepository.saveAndFlush(cart);
        return new CartDto(cart);
    }
}
