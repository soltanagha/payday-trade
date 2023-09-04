package com.payday.stockms.service;


import com.payday.common.event.dto.stock.StockDto;

import java.util.List;

public interface StockService {
    List<StockDto> getLatestStockPrices();
}
