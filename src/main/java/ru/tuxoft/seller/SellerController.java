package ru.tuxoft.seller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tuxoft.seller.dto.OrderEditDto;
import ru.tuxoft.seller.dto.SellerDto;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/work")
@Slf4j
public class SellerController {

    @Autowired
    SellerService sellerService;

    @RequestMapping(method = RequestMethod.GET, path = "/orders/{id}")
    public OrderEditDto getOrderById(
            @PathVariable("id") Long orderId
    ) {
        return sellerService.getOrderById(orderId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders")
    public List<OrderEditDto> getOrderList(
            @RequestParam(name = "workStatus", defaultValue = "all") String workStatus,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return sellerService.getOrderList(workStatus, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders/work-status")
    public OrderEditDto setWorkStatus(
            @RequestParam(name = "id") Long orderId,
            @RequestParam(name = "status") String workStatus
    ) {
        return sellerService.setWorkStatus(SecurityContextHolder.getContext().getAuthentication().getName(), orderId, workStatus);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders/getInWork")
    public OrderEditDto getOrderInWork(
            @RequestParam(name = "id") Long orderId
    ) {
        return sellerService.getOrderInWork(SecurityContextHolder.getContext().getAuthentication().getName(), orderId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders/status")
    public OrderEditDto setOrderStatus(
            @RequestParam(name = "id") Long orderId,
            @RequestParam(name = "status") String orderStatus,
            @RequestParam(name = "note") String description
    ) {
        return sellerService.setOrderStatus(SecurityContextHolder.getContext().getAuthentication().getName(), orderId, orderStatus, description);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders/worker")
    public OrderEditDto setOrderWorker(
            @RequestParam(name = "id") Long orderId,
            @RequestParam(name = "workerId") String workerId
    ) {
        return sellerService.setOrderWorker(SecurityContextHolder.getContext().getAuthentication().getName(), orderId, workerId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders/pay")
    public OrderEditDto setOrderPay(
            @RequestParam(name = "id") Long orderId,
            @RequestParam(name = "pay")BigDecimal pay
            ) {
        return sellerService.setOrderPay(SecurityContextHolder.getContext().getAuthentication().getName(), orderId, pay);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders/worker/search")
    public List<SellerDto> searchWorkers(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        return sellerService.searchWorkers(SecurityContextHolder.getContext().getAuthentication().getName(), query, start, pageSize);
    }

}
