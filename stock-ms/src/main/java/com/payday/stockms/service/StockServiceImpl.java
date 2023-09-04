package com.payday.stockms.service;

import com.payday.common.event.dto.factory.EventFactory;
import com.payday.common.event.dto.stock.StockDto;
import com.payday.common.event.dto.stock.StockUpdateEventDto;
import com.payday.common.event.enumeration.EventType;
import com.payday.common.event.producer.EventProducer;
import com.payday.common.exception.BadRequestException;
import com.payday.common.exception.ForbiddenException;
import com.payday.common.exception.util.ExceptionCodes;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {


    @Value("${alphavantage.api.key}")
    private String apiKey;
    private final RandomStockDataGenerator stockService;
    private final EventProducer<StockUpdateEventDto> stockPriceUpdateEventProducer;
    private final EventFactory<StockUpdateEventDto> eventFactory;

    @Override
    public List<StockDto> getLatestStockPrices() {
        RestTemplate restTemplate = new RestTemplate();

        String[] symbols = {"AAPL", "MSFT", "GOOGL"};;
        List<StockDto> stockDtoList = new ArrayList<>();
        for (String symbol : symbols) {
            try {
            String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCodeValue() == 200) {
                String responseBody = response.getBody();
                JSONObject jsonObject = new JSONObject(responseBody);
                if (jsonObject.has("Global Quote")) {
                    JSONObject globalQuote = jsonObject.getJSONObject("Global Quote");
                    if (globalQuote.has("05. price")) {
                        String latestPrice = globalQuote.getString("05. price");
                        stockDtoList.add(StockDto.builder()
                                .stockId(UUID.nameUUIDFromBytes(symbol.getBytes()).toString())
                                .symbol(symbol)
                                .price(Double.parseDouble(latestPrice)).build());

                    }
                }
            } else {
               throw new ForbiddenException(ExceptionCodes.API_LIMIT_EXCEED);
            }
            } catch (RestClientException e) {
                throw new BadRequestException(ExceptionCodes.CONNECT_EXCEPTION);
            } catch (JSONException e) {
                throw new BadRequestException(ExceptionCodes.JSON_EXCEPTION);
            }
        }
        return stockDtoList;
    }

    public List<StockDto> generateAndProduceStockUpdateEvent() {
        var stockList = stockService.getLatestStockPrices();
        var stockUpdateEventDto = StockUpdateEventDto
                .builder()
                .eventId(UUID.randomUUID().toString())
                .stockDtoList(stockList)
                .time(Instant.now())
                .timezone(ZoneOffset.UTC.getId())
                .build();

        var stockPriceUpdateEvent = eventFactory.createEvent(EventType.STOCK_PRICE_UPDATE.getName(),
                EventType.STOCK_PRICE_UPDATE.getTopic(), stockUpdateEventDto);

        stockPriceUpdateEventProducer.produce(stockPriceUpdateEvent);

        return stockList;
    }

}
