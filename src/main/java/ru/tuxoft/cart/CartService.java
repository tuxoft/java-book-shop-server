package ru.tuxoft.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.cart.domain.CartItemVO;
import ru.tuxoft.cart.domain.CartVO;
import ru.tuxoft.cart.domain.repository.CartItemRepository;
import ru.tuxoft.cart.domain.repository.CartRepository;
import ru.tuxoft.cart.dto.CartDto;
import ru.tuxoft.cart.mapper.CartMapper;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class CartService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartMapper cartMapper;

    public CartDto getCart(String id) {
        return cartMapper.cartVOToCartDto(cartRepository.findByUserId(id));
    }

    public CartDto addOrChangeBookToCart(String userId, Long bookId, int count) throws IllegalArgumentException {
        if (count<1) {
            throw new IllegalArgumentException("Ошибка добавления в корзину. Нельзя добавлять меньше 1 книги");
        }
        CartVO cart = cartRepository.findByUserId(userId);
        boolean find = false;
        for (CartItemVO cartItemVO: cart.getCartItemList()) {
            if (cartItemVO.getBook().getId() == bookId ) {
                cartItemVO.setCount(count);
                find = true;
                break;
            }
        }
        if (!find) {
            Optional<BookVO> book = bookRepository.findById(bookId);
            if (book.isPresent()) {
                cart.getCartItemList().add(new CartItemVO(cart, book.get(), count));
            } else {
                throw new IllegalArgumentException("Ошибка добавления в корзину. Книги с указанным id в БД не обнаружено");
            }
        }
        cartRepository.saveAndFlush(cart);
        return cartMapper.cartVOToCartDto(cart);
    }

    public CartDto deleteBookToCart(String userId, Long bookId) throws IllegalArgumentException {
        CartVO cart = cartRepository.findByUserId(userId);
        int deleteIndex = -1;
        for (int i=0; i< cart.getCartItemList().size(); i++) {
            if (cart.getCartItemList().get(i).getBook().getId() == bookId ) {
                deleteIndex = i;
                break;
            }
        }
        if (deleteIndex != -1) {
            cartItemRepository.delete(cart.getCartItemList().remove(deleteIndex));
        } else {
            throw new IllegalArgumentException("Ошибка удаления из корзины. Книги с указанным id в корзине не обнаружено");
        }
        cartRepository.saveAndFlush(cart);
        return cartMapper.cartVOToCartDto(cart);
    }

    public CartDto deleteBookToCart(String userId, List<Long> bookIdList) throws IllegalArgumentException {
        CartVO cart = cartRepository.findByUserId(userId);
        for (Long bookId: bookIdList) {
            int deleteIndex = -1;
            for (int i = 0; i < cart.getCartItemList().size(); i++) {
                if (cart.getCartItemList().get(i).getBook().getId() == bookId) {
                    deleteIndex = i;
                    break;
                }
            }
            if (deleteIndex != -1) {
                cartItemRepository.delete(cart.getCartItemList().remove(deleteIndex));
            } else {
                throw new IllegalArgumentException("Ошибка удаления из корзины. Книги с указанным id в корзине не обнаружено");
            }
        }
        cartRepository.saveAndFlush(cart);
        return cartMapper.cartVOToCartDto(cart);
    }
}
