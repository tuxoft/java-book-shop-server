package ru.tuxoft.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tuxoft.cart.dto.CartDto;

import javax.xml.ws.Response;

@RestController
@Slf4j
public class CartController {

    private String userId = "user1";

    @Autowired
    CartService cartService;

    @RequestMapping(method = RequestMethod.GET, path = "/cart")
    public CartDto getCart() {
        return cartService.getCart(userId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/cart")
    public ResponseEntity addBookToCart(
            @RequestParam(name = "id") Long bookId,
            @RequestParam(name = "count", defaultValue = "1") int count
    ) {
        try {
            return new ResponseEntity<>(cartService.addOrChangeBookToCart(userId, bookId, count),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/cart")
    public ResponseEntity changeBookToCart(
            @RequestParam(name = "id") Long bookId,
            @RequestParam(name = "count", defaultValue = "1") int count
    ) {
        try {
            return new ResponseEntity<>(cartService.addOrChangeBookToCart(userId, bookId, count),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/cart")
    public ResponseEntity deleteBookToCart(
            @RequestParam(name = "id") Long bookId
    ) {
        try {
            return new ResponseEntity<>(cartService.deleteBookToCart(userId, bookId),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
