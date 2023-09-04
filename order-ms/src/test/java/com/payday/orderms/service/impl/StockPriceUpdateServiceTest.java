package com.payday.orderms.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.payday.common.event.dto.stock.StockUpdateEventDto;
import com.payday.orderms.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StockPriceUpdateServiceTest {

    @InjectMocks
    private StockPriceUpdateService stockPriceUpdateService;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessInvalidOrders() {
        StockUpdateEventDto stockUpdateEventDto = new StockUpdateEventDto();

        stockPriceUpdateService.processOrders(stockUpdateEventDto);

        verify(orderService, times(0)).findOrders(anyString(), anyDouble());
    }
}