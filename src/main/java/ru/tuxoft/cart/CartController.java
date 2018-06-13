package ru.tuxoft.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tuxoft.cart.dto.CartDto;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@Slf4j
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping(method = RequestMethod.GET, path = "/cart")
    public CartDto getCart(@CookieValue(value = "CARTUUID", required = false) String cartCookies,
                           HttpServletResponse response) {
        String userId = cartService.getUserId(cartCookies, response);
        return cartService.getCart(userId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/cart")
    public ResponseEntity addBookToCart(
            @CookieValue(value = "CARTUUID", required = false) String cartCookies,
            @RequestParam(name = "id") Long bookId,
            @RequestParam(name = "count", defaultValue = "1") int count,
            HttpServletResponse response
    ) {
        String userId = cartService.getUserId(cartCookies, response);
        try {
            return new ResponseEntity<>(cartService.addOrChangeOrDeleteBookToCart(userId, bookId, count),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/cart")
    public ResponseEntity changeBookToCart(
            @CookieValue(value = "CARTUUID", required = false) String cartCookies,
            @RequestParam(name = "id") Long bookId,
            @RequestParam(name = "count", defaultValue = "1") int count,
            HttpServletResponse response
    ) {
        String userId = cartService.getUserId(cartCookies, response);
        try {
            return new ResponseEntity<>(cartService.addOrChangeOrDeleteBookToCart(userId, bookId, count),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/cart")
    public ResponseEntity deleteBookToCart(
            @CookieValue(value = "CARTUUID", required = false) String cartCookies,
            @RequestParam(name = "id") Long bookId,
            HttpServletResponse response
    ) {
        String userId = cartService.getUserId(cartCookies, response);
        try {
            return new ResponseEntity<>(cartService.deleteBookToCart(userId, bookId),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/cart/list")
    public ResponseEntity deleteBookToCart(
            @CookieValue(value = "CARTUUID", required = false) String cartCookies,
            @RequestParam(name = "ids") List<Long> bookIdList,
            HttpServletResponse response
    ) {
        String userId = cartService.getUserId(cartCookies, response);
        try {
            return new ResponseEntity<>(cartService.deleteBookToCart(userId, bookIdList),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
