package com.payday.orderms.service.impl;

import com.payday.common.event.dto.account.BalanceReserveDto;
import com.payday.common.event.dto.account.BalanceReserveEventDto;
import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.enumeration.EventType;
import com.payday.common.event.producer.EventProducer;
import com.payday.common.exception.NotFoundException;
import com.payday.common.exception.util.ExceptionCodes;
import com.payday.orderms.dto.OrderDto;
import com.payday.orderms.dto.mapper.OrderMapper;
import com.payday.orderms.entity.Order;
import com.payday.orderms.repository.OrderRepository;
import com.payday.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EventProducer<BalanceReserveEventDto> balanceSufficientEventDtoEventProducer;
    private final EventFactory<BalanceReserveEventDto> eventFactory;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        var order = OrderMapper.INSTANCE.toEntity(orderDto);
        order.setAccountEmail(currentUserEmail);
        order.setIsReserved(false);
        order.setIsOpen(true);
        var savedOrder = orderRepository.save(order);
        produceEvent(savedOrder.getId(),currentUserEmail,orderDto.getFromSymbol(),orderDto.getAmount());

        return OrderMapper.INSTANCE.toDto(savedOrder);
    }

    @Override
    public Order updateOrder(Order order) {
        if (!orderRepository.existsById(order.getId()))
            throw new NotFoundException(ExceptionCodes.METHOD_ARGUMENT_NOT_VALID);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }


    @Override
    public List<Order> findOrders(String symbol, double price) {
        return orderRepository.findOrders(symbol, price);
    }

    @Override
    public void orderBalanceReserved(Long orderId) {
        var orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty())
            throw new NotFoundException(ExceptionCodes.METHOD_ARGUMENT_NOT_VALID);

        var order = orderOpt.get();
        order.setIsReserved(true);
        orderRepository.save(order);
    }

    void produceEvent(Long orderId,String email,String symbol,Double amount) {
        var balanceSufficientDto = new BalanceReserveDto(orderId,email,symbol,amount);
        var balanceSufficientEventDto = BalanceReserveEventDto
                .builder()
                .eventId(UUID.randomUUID().toString())
                .balanceReserveDto(balanceSufficientDto)
                .time(Instant.now())
                .timezone(ZoneOffset.UTC.getId())
                .build();

        var balanceSufficientEvent = eventFactory.createEvent(EventType.BALANCE_RESERVE.getName(),
                EventType.BALANCE_RESERVE.getTopic(), balanceSufficientEventDto);

        balanceSufficientEventDtoEventProducer.produce(balanceSufficientEvent);
    }
}
