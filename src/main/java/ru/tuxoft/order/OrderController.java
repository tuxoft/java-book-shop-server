package ru.tuxoft.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tuxoft.book.BookService;
import ru.tuxoft.book.dto.AuthorDto;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.book.dto.CategoryDto;
import ru.tuxoft.cart.CartService;
import ru.tuxoft.order.dto.*;
import ru.tuxoft.paging.ListResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, path = "/order/template")
    public OrderDto getTemplateOrder() {
        return orderService.getTemplateOrder(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/order", produces = "application/json")
    public String updateOrder(
            @RequestBody OrderDto orderDto
    ) {
        return orderService.updateOrder(orderDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/order/{id}")
    public OrderDto getOrderById(
            @PathVariable("id") Long orderId
    ) {
        return orderService.getOrderById(SecurityContextHolder.getContext().getAuthentication().getName(), orderId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cities")
    public List<ShopCityDto> getCities() {
        return orderService.getShopCities();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pickupPoint")
    public List<PickupPointDto> getPickupPoint() {
        return orderService.getPickupPoint();
    }

}
