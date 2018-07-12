package ru.tuxoft.seller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tuxoft.order.enums.StatusEnum;
import ru.tuxoft.paging.ListResult;
import ru.tuxoft.paging.Meta;
import ru.tuxoft.paging.Paging;
import ru.tuxoft.seller.domain.ActionVO;
import ru.tuxoft.seller.domain.OrderEditVO;
import ru.tuxoft.seller.domain.SellerVO;
import ru.tuxoft.seller.domain.enums.WorkStatusEnum;
import ru.tuxoft.seller.domain.repository.OrderEditRepository;
import ru.tuxoft.seller.domain.repository.SellerRepository;
import ru.tuxoft.seller.dto.OrderEditDto;
import ru.tuxoft.seller.dto.SellerDto;
import ru.tuxoft.seller.mapper.OrderEditMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SellerService {

    @Autowired
    OrderEditRepository orderEditRepository;

    @Autowired
    OrderEditMapper orderEditMapper;

    @Autowired
    SellerRepository sellerRepository;

    public OrderEditDto getOrderById(Long orderId) {
        Optional<OrderEditVO> orderEditOptional = orderEditRepository.findById(orderId);
        if (orderEditOptional.isPresent()) {
            return orderEditMapper.orderEditVOToOrderEditDto(orderEditOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса ордера. Неверный orderId");
        }
    }

    public List<OrderEditDto> getOrderList(String workStatus, int start, int pageSize, String sort, String order) {
        int page = start / pageSize;
        if (workStatus.equals("all")) {
            return orderEditRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> orderEditMapper.orderEditVOToOrderEditDto(e)).collect(Collectors.toList());
        } else {
            for (WorkStatusEnum workStatusEnum: WorkStatusEnum.values()) {
                if (workStatusEnum.getValue().equals(workStatus)) {
                    return orderEditRepository.findByWorkStatus(workStatusEnum, PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> orderEditMapper.orderEditVOToOrderEditDto(e)).collect(Collectors.toList());
                }
            }
            throw new IllegalArgumentException("Ошибка запроса списка ордеров. Неверный workStatus");
        }
    }

    private ActionVO createNewAction(String field, String oldValue, String newValue, String description, Date changeDate, String userId, OrderEditVO order) {
        ActionVO newAction = new ActionVO();
        newAction.setChangeField(field);
        newAction.setOldValue(oldValue);
        newAction.setNewValue(newValue);
        newAction.setDescription(description);
        newAction.setChangeDate(changeDate);
        newAction.setOrder(order);
        newAction.setWorker(sellerRepository.findByUserId(userId));
        return newAction;
    }

    public OrderEditDto setWorkStatus(String userId, Long orderId, String workStatus) {
        Optional<OrderEditVO> orderEditOptional = orderEditRepository.findById(orderId);
        if (orderEditOptional.isPresent()) {
            WorkStatusEnum oldWorkStatusEnum = orderEditOptional.get().getOrderWorkInfo().getWorkStatus();
            WorkStatusEnum newWorkStatusEnum = null;
            for (WorkStatusEnum workStatusEnum : WorkStatusEnum.values()) {
                if (workStatus.equals(workStatusEnum.getValue())) {
                    newWorkStatusEnum = workStatusEnum;
                    break;
                }
            }
            if (newWorkStatusEnum != null) {
                orderEditOptional.get().getOrderWorkInfo().setWorkStatus(newWorkStatusEnum);
                Date changeDate = new Date();
                orderEditOptional.get().getChangeHistory().add(
                        createNewAction(
                                "workStatus", oldWorkStatusEnum.name(), newWorkStatusEnum.name(), null, changeDate, userId, orderEditOptional.get()
                        )
                );

            } else {
                throw new IllegalArgumentException("Ошибка задания workStatus. Неверный workStatus");
            }
            OrderEditVO newOrderEditVO = orderEditRepository.saveAndFlush(orderEditOptional.get());
            return orderEditMapper.orderEditVOToOrderEditDto(newOrderEditVO);
        } else {
            throw new IllegalArgumentException("Ошибка задания workStatus. Неверный orderId");
        }

    }

    public OrderEditDto setOrderStatus(String userId, Long orderId, String orderStatus, String description) {
        Optional<OrderEditVO> orderEditOptional = orderEditRepository.findById(orderId);
        if (orderEditOptional.isPresent()) {
            StatusEnum oldStatusEnum = orderEditOptional.get().getStatus();
            StatusEnum newStatusEnum = null;
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (orderStatus.equals(statusEnum.getText())) {
                    newStatusEnum = statusEnum;
                    break;
                }
            }
            if (newStatusEnum != null) {
                orderEditOptional.get().setStatus(newStatusEnum);
                Date changeDate = new Date();
                orderEditOptional.get().getChangeHistory().add(
                        createNewAction(
                                "orderStatus", oldStatusEnum.name(), newStatusEnum.name(), description, changeDate, userId, orderEditOptional.get()
                        )
                );
            } else {
                throw new IllegalArgumentException("Ошибка задания orderStatus. Неверный orderStatus");
            }
            OrderEditVO newOrderEditVO = orderEditRepository.saveAndFlush(orderEditOptional.get());
            return orderEditMapper.orderEditVOToOrderEditDto(newOrderEditVO);
        } else {
            throw new IllegalArgumentException("Ошибка задания orderStatus. Неверный orderId");
        }
    }

    public OrderEditDto setOrderWorker(String userId, Long orderId, String workerId) {
        Optional<OrderEditVO> orderEditOptional = orderEditRepository.findById(orderId);
        if (orderEditOptional.isPresent()) {
            SellerVO oldWorker = orderEditOptional.get().getOrderWorkInfo().getWorker();
            if (oldWorker.getUserId().equals(userId)) {
                return orderEditMapper.orderEditVOToOrderEditDto(orderEditOptional.get());
            }
            SellerVO newWorker = sellerRepository.findByUserId(workerId);
            if (newWorker != null) {
                orderEditOptional.get().getOrderWorkInfo().setWorker(newWorker);
                Date changeDate = new Date();
                orderEditOptional.get().getChangeHistory().add(
                        createNewAction(
                                "worker", oldWorker != null ? oldWorker.getUserId() : "не назначен", workerId, null, changeDate, userId, orderEditOptional.get()
                        )
                );
            } else {
                throw new IllegalArgumentException("Ошибка задания worker. Неверный workerId");
            }
            OrderEditVO newOrderEditVO = orderEditRepository.saveAndFlush(orderEditOptional.get());
            return orderEditMapper.orderEditVOToOrderEditDto(newOrderEditVO);
        } else {
            throw new IllegalArgumentException("Ошибка задания worker. Неверный orderId");
        }
    }

    public OrderEditDto setOrderPay(String userId, Long orderId, BigDecimal pay) {
        Optional<OrderEditVO> orderEditOptional = orderEditRepository.findById(orderId);
        if (orderEditOptional.isPresent()) {
            BigDecimal oldPayFor = orderEditOptional.get().getPayFor();
            BigDecimal oldToPay = orderEditOptional.get().getToPay();
            BigDecimal newPayFor = oldPayFor.add(pay);
            BigDecimal newToPay = oldToPay.subtract(pay);
            if (newToPay.compareTo(BigDecimal.ZERO) >= 0) {
                orderEditOptional.get().setPayFor(newPayFor);
                orderEditOptional.get().setToPay(newToPay);
                Date changeDate = new Date();
                orderEditOptional.get().getChangeHistory().add(
                        createNewAction(
                                "payFor", oldPayFor.toString(), newPayFor.toString(), null, changeDate, userId, orderEditOptional.get()
                        )
                );
                orderEditOptional.get().getChangeHistory().add(
                        createNewAction(
                                "toPay", oldToPay.toString(), newToPay.toString(), null, changeDate, userId, orderEditOptional.get()
                        )
                );
            } else {
                throw new IllegalArgumentException("Ошибка задания pay. Оплата больше чем надо");
            }
            OrderEditVO newOrderEditVO = orderEditRepository.saveAndFlush(orderEditOptional.get());
            return orderEditMapper.orderEditVOToOrderEditDto(newOrderEditVO);
        } else {
            throw new IllegalArgumentException("Ошибка задания pay. Неверный orderId");
        }
    }

    public List<SellerDto> searchWorkers(String userId, String query, int start, int pageSize) {
        int page = start / pageSize;
        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');
        return sellerRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> orderEditMapper.sellerVOToSellerDto(e)).collect(Collectors.toList());
    }

    public OrderEditDto getOrderInWork(String userId, Long orderId) {
        setWorkStatus(userId, orderId, "inWork");
        return setOrderWorker(userId, orderId, userId);
    }
}
