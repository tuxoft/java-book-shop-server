package ru.tuxoft.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
        log.debug("userId {}", id);
        CartVO cart = cartRepository.findByUserId(id);
        if(cart== null)cart=new CartVO();
        if (cart.getCartItemList() != null) {
            cart.getCartItemList().sort((left, right) -> left.getId() > right.getId() ? -1 : (left.getId() < right.getId()) ? 1 : 0);
        }
        return cartMapper.cartVOToCartDto(cart);
    }

    public CartDto addOrChangeOrDeleteBookToCart(String userId, Long bookId, int count) throws IllegalArgumentException {
        if (count < 1) {
            return deleteBookToCart(userId, bookId);
        }
        CartVO cart = cartRepository.findByUserId(userId);
        boolean find = false;
        if (cart.getCartItemList() == null) {
            cart.setCartItemList(new ArrayList<>());
        }
        for (CartItemVO cartItemVO : cart.getCartItemList()) {
            if (cartItemVO.getBook().getId() == bookId) {
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
        cart.getCartItemList().sort((left, right) -> left.getId() > right.getId() ? -1 : (left.getId() < right.getId()) ? 1 : 0);
        return cartMapper.cartVOToCartDto(cart);
    }

    public CartDto deleteBookToCart(String userId, Long bookId) throws IllegalArgumentException {
        CartVO cart = cartRepository.findByUserId(userId);
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
        cartRepository.saveAndFlush(cart);
        cart.getCartItemList().sort((left, right) -> left.getId() > right.getId() ? -1 : (left.getId() < right.getId()) ? 1 : 0);
        return cartMapper.cartVOToCartDto(cart);
    }

    public CartDto deleteBookToCart(String userId, List<Long> bookIdList) throws IllegalArgumentException {
        CartVO cart = cartRepository.findByUserId(userId);
        for (Long bookId : bookIdList) {
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
        cart.getCartItemList().sort((left, right) -> left.getId() > right.getId() ? -1 : (left.getId() < right.getId()) ? 1 : 0);
        return cartMapper.cartVOToCartDto(cart);
    }

    public String getUserId(String cartCookies, HttpServletResponse response) {
        String user;
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null;
        if (isAuthenticated) {
            user = SecurityContextHolder.getContext().getAuthentication().getName();
            if (cartCookies != null) {
                Cookie cookie = new Cookie("CARTUUID", UUID.randomUUID().toString());
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        } else {
            if (cartCookies != null) {
                user = cartCookies;
            } else {
                Cookie cookie = new Cookie("CARTUUID", UUID.randomUUID().toString());
                log.debug("For user generate new cookie CARTUUID: {}", cookie);
                response.addCookie(cookie);
                user = cookie.getValue();
            }
        }

        if (cartRepository.findCountCartByUserId(user) == 0) {
            CartVO cart = new CartVO();
            cart.setUserId(user);
            cart.setTemporary(!isAuthenticated);
            if (isAuthenticated && cartCookies != null) {
                moveCartItemFromTemproraryCartToUserCart(cart, cartCookies);
            }
            cartRepository.saveAndFlush(cart);
        } else {
            CartVO cart = cartRepository.findByUserId(user);
            if (isAuthenticated && cartCookies != null) {
                moveCartItemFromTemproraryCartToUserCart(cart, cartCookies);
            }
            cartRepository.saveAndFlush(cart);
        }
        return user;
    }

    private void moveCartItemFromTemproraryCartToUserCart(CartVO cart, String temporaryUser) {
        CartVO temporaryCard = cartRepository.findByUserId(temporaryUser);
        if (temporaryCard != null && temporaryCard.getCartItemList() != null) {
            if (cart.getCartItemList() == null) {
                cart.setCartItemList(new ArrayList<>());
            }
            for (CartItemVO cartItemVO : temporaryCard.getCartItemList()) {
                if (!cart.getCartItemList().stream().anyMatch((cartItem) -> {
                    if (cartItem.getBook().getId() == cartItemVO.getBook().getId()) {
                        cartItem.setCount(cartItem.getCount() + cartItemVO.getCount());
                        return true;
                    } else {
                        return false;
                    }
                })) {
                    cartItemVO.setCart(cart);
                    cart.getCartItemList().add(cartItemVO);
                }
            }
            cartItemRepository.deleteByCartId(temporaryCard.getId());
            cartItemRepository.flush();
            temporaryCard.setCartItemList(null);
        }
        if (temporaryCard != null) {
            cartRepository.deleteById(temporaryCard.getId());
            cartItemRepository.flush();
        }

    }

    public void cleanCart(String userId) {
        CartVO cart = cartRepository.findByUserId(userId);
        cartItemRepository.deleteByCartId(cart.getId());
        cartItemRepository.flush();
        cart.setCartItemList(new ArrayList<>());
        cartRepository.saveAndFlush(cart);
    }
}
