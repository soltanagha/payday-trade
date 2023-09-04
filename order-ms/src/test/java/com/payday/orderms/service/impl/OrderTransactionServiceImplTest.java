package com.payday.orderms.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.payday.orderms.entity.OrderTransaction;
import com.payday.orderms.repository.OrderTransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OrderTransactionServiceImplTest {

    @InjectMocks
    private OrderTransactionServiceImpl orderTransactionService;

    @Mock
    private OrderTransactionsRepository orderTransactionsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        OrderTransaction orderTransaction = new OrderTransaction();
        when(orderTransactionsRepository.save(orderTransaction)).thenReturn(orderTransaction);

        OrderTransaction result = orderTransactionService.create(orderTransaction);

        assertNotNull(result);
    }

    @Test
    void testFindById() {
        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setId(1L);

        when(orderTransactionsRepository.findById(1L)).thenReturn(Optional.of(orderTransaction));

        Optional<OrderTransaction> result = orderTransactionService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindAll() {
        OrderTransaction orderTransaction1 = new OrderTransaction();
        OrderTransaction orderTransaction2 = new OrderTransaction();

        when(orderTransactionsRepository.findAll()).thenReturn(Arrays.asList(orderTransaction1, orderTransaction2));

        List<OrderTransaction> result = orderTransactionService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdate() {
        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setId(1L);

        when(orderTransactionsRepository.existsById(1L)).thenReturn(true);
        when(orderTransactionsRepository.save(orderTransaction)).thenReturn(orderTransaction);

        OrderTransaction result = orderTransactionService.update(orderTransaction);

        assertNotNull(result);
    }

    @Test
    void testUpdateNotFound() {
        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setId(1L);

        when(orderTransactionsRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> orderTransactionService.update(orderTransaction));
    }

    @Test
    void testDelete() {
        doNothing().when(orderTransactionsRepository).deleteById(1L);

        orderTransactionService.delete(1L);

        verify(orderTransactionsRepository, times(1)).deleteById(1L);
    }
}
