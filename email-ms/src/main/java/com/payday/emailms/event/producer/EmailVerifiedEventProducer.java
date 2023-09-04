package com.payday.emailms.event.producer;

import com.payday.common.event.dto.EventDto;
import com.payday.common.event.producer.EventProducer;
import com.payday.emailms.event.dto.EmailVerifiedEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailVerifiedEventProducer implements EventProducer<EmailVerifiedEventDto> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(EventDto<EmailVerifiedEventDto> eventDto) {
        kafkaTemplate.send(eventDto.getTopic(), eventDto);
    }

}
