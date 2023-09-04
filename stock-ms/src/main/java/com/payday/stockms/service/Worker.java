package com.payday.stockms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Worker {

    private final StockServiceImpl stockService;

    @Scheduled(fixedRate = 30000) // 30sec
    public void runEvery30Seconds() {
        stockService.generateAndProduceStockUpdateEvent();
    }
}
