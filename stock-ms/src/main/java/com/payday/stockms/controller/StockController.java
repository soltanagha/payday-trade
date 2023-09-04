package com.payday.stockms.controller;


import com.payday.common.event.dto.stock.StockDto;
import com.payday.stockms.service.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockServiceImpl stockService;

    @GetMapping
    public List<StockDto> getAll() {
        return stockService.generateAndProduceStockUpdateEvent();
    }
}
