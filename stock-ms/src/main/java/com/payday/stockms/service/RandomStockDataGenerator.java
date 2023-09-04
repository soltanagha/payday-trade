package com.payday.stockms.service;

import com.payday.common.event.dto.stock.StockDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RandomStockDataGenerator implements StockService {

    public List<StockDto> getLatestStockPrices() {
        String[] symbols = {"AAPL", "MSFT", "GOOGL"};
        List<StockDto> stockDtoList = new ArrayList<>();

        for (String symbol : symbols) {
            double price = generateRandomPrice();

            StockDto stock = StockDto.builder()
                    .stockId(UUID.nameUUIDFromBytes(symbol.getBytes()).toString())
                    .symbol(symbol)
                    .price(price)
                    .build();

            stockDtoList.add(stock);
        }

        return stockDtoList;
    }

    public double generateRandomPrice() {
        return 100.0 + Math.random() * 200.0; // Generates a random price between 100 and 300
    }
}
