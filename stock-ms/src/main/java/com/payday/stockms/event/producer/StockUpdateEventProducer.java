package com.payday.stockms.event.producer;

import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.stock.StockUpdateEventDto;
import com.payday.common.event.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockUpdateEventProducer implements EventProducer<StockUpdateEventDto> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(EventDto<StockUpdateEventDto> eventDto) {
        kafkaTemplate.send(eventDto.getTopic(), eventDto);
    }

}
