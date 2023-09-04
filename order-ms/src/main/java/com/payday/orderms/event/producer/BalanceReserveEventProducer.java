package com.payday.orderms.event.producer;

import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.account.BalanceReserveEventDto;
import com.payday.common.event.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceReserveEventProducer implements EventProducer<BalanceReserveEventDto> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(EventDto<BalanceReserveEventDto> eventDto) {
        kafkaTemplate.send(eventDto.getTopic(), eventDto);
    }

}
