package com.payday.orderms.event.producer;

import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.account.BalanceReleaseEventDto;
import com.payday.common.event.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReleaseBalanceEventProducer implements EventProducer<BalanceReleaseEventDto> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(EventDto<BalanceReleaseEventDto> eventDto) {
        kafkaTemplate.send(eventDto.getTopic(), eventDto);
    }

}
