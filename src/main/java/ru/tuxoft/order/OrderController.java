package ru.tuxoft.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tuxoft.order.enums.SendTypeEnum;
import ru.tuxoft.order.dto.*;

import java.util.List;

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

    @RequestMapping(method = RequestMethod.GET, path = "/pickupPoint/{id}")
    public List<PickupPointDto> getPickupPoint(
            @PathVariable("id") Long cityId
    ) {
        return orderService.getPickupPoint(cityId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/courierService/{id}")
    public List<CourierServiceDto> getCourierService(
            @PathVariable("id") Long cityId
    ) {
        return orderService.getCourierService(cityId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/mailService/{id}")
    public List<MailServiceDto> getMailService(
            @PathVariable("id") Long cityId
    ) {
        return orderService.getMailService(cityId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/paymentMethod")
    public List<PaymentMethodDto> getPaymentMethod(
            @RequestParam(name = "sendType")String sendType,
            @RequestParam(name = "sendOrgId") Long sendOrgId
            ) {
        return orderService.getPaymentMethod(sendType, sendOrgId);
    }

}
