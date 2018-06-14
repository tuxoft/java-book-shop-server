package ru.tuxoft.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    BookService bookService;
    @Autowired
    CartService cartService;
    private String userId = "user1";

    @RequestMapping(method = RequestMethod.GET, path = "/order")
    public ResponseEntity getOrder() {
        OrderDto od = new OrderDto();
        od.setOrderItemList(new ArrayList<OrderItemDto>());
        od.getOrderItemList().addAll(cartService.getCart(userId).getCartItemList()
                .stream().map(e -> new OrderItemDto(e)).collect(Collectors.toList()));
        od.setDiscount(new BigDecimal(0));
        od.setPayFor(new BigDecimal(100));
        od.setToPay(new BigDecimal(150));
        od.setTotalCost(new BigDecimal(200));
        od.setSendPrice(new BigDecimal(0));
        od.setAddr(new AddressDto());
        log.info("order", od.getTotalCost());
        return new ResponseEntity<>(od, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/order", produces = "application/json")
    public ResponseEntity makeOrder(
            @RequestBody OrderDto orderDto
    ) {
        orderDto.setId(2L);
        return new ResponseEntity<>("/pay", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/order/{id}")
    public ResponseEntity getOrderById(
            @PathVariable("id") Long id
    ) {
        OrderDto od = new OrderDto();
        od.setOrderItemList(new ArrayList<OrderItemDto>());
        od.getOrderItemList().addAll(cartService.getCart(userId).getCartItemList()
                .stream().map(e -> new OrderItemDto(e)).collect(Collectors.toList()));
        od.setDiscount(new BigDecimal(0));
        od.setPayFor(new BigDecimal(100));
        od.setToPay(new BigDecimal(150));
        od.setTotalCost(new BigDecimal(200));
        od.setSendPrice(new BigDecimal(50));
        od.setAddr(new AddressDto());
        od.setStatus("FILLED");
        log.info("order", od.getTotalCost());
        return new ResponseEntity<>(od, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cities")
    public List<CityDto> getCities() {
        List<CityDto> cities = new ArrayList<>();
        CityDto city = new CityDto();
        city.setId(1L);
        city.setName("U-U");
        city.setCoords(new ArrayList<>());
        city.getCoords().add(51.811704);
        city.getCoords().add(107.623283);
        cities.add(city);
        CityDto city2 = new CityDto();
        city2.setId(2L);
        city2.setName("Moscov");
        city2.setCoords(new ArrayList<>());
        city2.getCoords().add(55.76);
        city2.getCoords().add(37.64);
        cities.add(city2);
        return cities;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pickupPoint/{id}")
    public List<PickupPointDto> getPickupPoint(@PathVariable("id") Long citiId) {
        List<PickupPointDto> pickupPoints = new ArrayList<>();
        PickupPointDto pickupPoint = new PickupPointDto();
        pickupPoint.setOrgId(1L);
        pickupPoint.setOrgName("U-U");
        pickupPoint.setCoords(new ArrayList<>());
        pickupPoint.getCoords().add(51.811704);
        pickupPoint.getCoords().add(107.623283);
        pickupPoint.setOrgAddr("Улан-Удэ, Республика Бурятия, 670961, Улан-Удэ, Смолина ул., 54");
        pickupPoint.setOrgWorkPeriod("09:-20:00");
        pickupPoint.setPayCase("Наличные и банковская карта");

        if(citiId==1L)pickupPoints.add(pickupPoint);

        PickupPointDto pickupPoint2 = new PickupPointDto();
        pickupPoint2.setOrgId(1L);
        pickupPoint2.setOrgName("Moscov");
        pickupPoint2.setCoords(new ArrayList<>());
        pickupPoint2.getCoords().add(55.76);
        pickupPoint2.getCoords().add(37.64);
        pickupPoint2.setOrgAddr("Улан-Удэ, Республика Бурятия, 670961, Улан-Удэ, Смолина ул., 54");
        pickupPoint2.setOrgWorkPeriod("09:-20:00");
        pickupPoint2.setPayCase("Наличные и банковская карта");

        if(citiId==2L)pickupPoints.add(pickupPoint2);

        return pickupPoints;
    }

}
