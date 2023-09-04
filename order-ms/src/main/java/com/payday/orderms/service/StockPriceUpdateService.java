package com.payday.orderms.service;


import com.payday.common.event.dto.stock.StockUpdateEventDto;

public interface StockPriceUpdateService {
    void processOrders(StockUpdateEventDto stockUpdateEventDto);
}
