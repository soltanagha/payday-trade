package com.payday.orderms.event.consumer;

import com.payday.common.event.consumer.EventConsumer;
import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.stock.StockUpdateEventDto;
import com.payday.orderms.service.impl.StockPriceUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceUpdateEventConsumer implements EventConsumer<StockUpdateEventDto> {

    private final StockPriceUpdateService stockPriceUpdateService;

    @Override
    @KafkaListener(topics = "stock-price-update", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void consume(EventDto<StockUpdateEventDto> eventDto) {
        stockPriceUpdateService.processOrders(eventDto.getBody());
        log.info("Consumed prices: " + eventDto.getBody().getStockDtoList().toString());
    }
}
