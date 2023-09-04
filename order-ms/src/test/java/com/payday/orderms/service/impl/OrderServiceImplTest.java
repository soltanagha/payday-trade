package com.payday.orderms.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.payday.common.event.dto.account.BalanceReserveEventDto;
import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.producer.EventProducer;
import com.payday.common.exception.NotFoundException;
import com.payday.orderms.dto.OrderDto;
import com.payday.orderms.dto.mapper.OrderMapper;
import com.payday.orderms.entity.Order;
import com.payday.orderms.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EventProducer<BalanceReserveEventDto> balanceSufficientEventDtoEventProducer;

    @Mock
    private EventFactory<BalanceReserveEventDto> eventFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getName()).thenReturn("test@example.com");

        OrderDto orderDto = new OrderDto();
        orderDto.setFromSymbol("BTC");
        orderDto.setAmount(10.0);

        Order order = OrderMapper.INSTANCE.toEntity(orderDto);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto result = orderService.createOrder(orderDto);

        assertNotNull(result);
        assertEquals("BTC", result.getFromSymbol());
        assertEquals(10.0, result.getAmount());
    }

    @Test
    void testUpdateOrderNotFound() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> orderService.updateOrder(order));
    }

    @Test
    void testOrderBalanceReserved() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.orderBalanceReserved(1L);
    }

    @Test
    void testOrderBalanceReservedNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.orderBalanceReserved(1L));
    }
}
