package com.payday.accountms.event.producer;

import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.account.BalanceReservedEventDto;
import com.payday.common.event.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceReservedEventProducer implements EventProducer<BalanceReservedEventDto> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(EventDto<BalanceReservedEventDto> eventDto) {
        kafkaTemplate.send(eventDto.getTopic(), eventDto);
    }

}
