package com.payday.stockms.controller;
import com.payday.common.event.dto.stock.StockDto;
import com.payday.stockms.service.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class StockControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private StockController stockController;

    @Mock
    private StockServiceImpl stockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    public void testGetAllStocks() throws Exception {
        StockDto stockDto1 = StockDto.builder().stockId("someId1").symbol("AAPL").price(150.0).build();
        StockDto stockDto2 = StockDto.builder().stockId("someId2").symbol("MSFT").price(200.0).build();
        List<StockDto> stockDtoList = Arrays.asList(stockDto1, stockDto2);

        when(stockService.generateAndProduceStockUpdateEvent()).thenReturn(stockDtoList);

        mockMvc.perform(get("/api/stock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[0].price").value(150.0))
                .andExpect(jsonPath("$[1].symbol").value("MSFT"))
                .andExpect(jsonPath("$[1].price").value(200.0));
    }
}
