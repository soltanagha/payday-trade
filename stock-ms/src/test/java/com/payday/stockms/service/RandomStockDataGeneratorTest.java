package com.payday.stockms.service;

import com.payday.common.event.dto.stock.StockDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RandomStockDataGeneratorTest {

    private RandomStockDataGenerator randomStockDataGenerator;

    @BeforeEach
    public void setUp() {
        randomStockDataGenerator = new RandomStockDataGenerator();
    }

    @Test
    public void testGetLatestStockPrices() {
        List<StockDto> stockDtoList = randomStockDataGenerator.getLatestStockPrices();

        // Asserts that 3 stocks are generated
        assertEquals(3, stockDtoList.size());

        // Asserts that all stocks have valid symbols, stockId and price within the range
        stockDtoList.forEach(stockDto -> {
            assertNotNull(stockDto.getSymbol());
            assertNotNull(stockDto.getStockId());
            double price = stockDto.getPrice();
            assertEquals(true, price >= 100.0 && price <= 300.0);
        });
    }

    @Test
    public void testGenerateRandomPrice() {
        double price = randomStockDataGenerator.generateRandomPrice();

        // Asserts that the price is within the expected range
        assertEquals(true, price >= 100.0 && price <= 300.0);
    }
}
