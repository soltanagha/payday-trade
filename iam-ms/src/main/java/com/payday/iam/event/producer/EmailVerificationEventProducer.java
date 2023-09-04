package com.payday.iam.event.producer;

import com.payday.common.event.dto.EventDto;
import com.payday.common.event.producer.EventProducer;
import com.payday.iam.event.dto.EmailVerificationEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailVerificationEventProducer implements EventProducer<EmailVerificationEventDto> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(EventDto<EmailVerificationEventDto> eventDto) {
        kafkaTemplate.send(eventDto.getTopic(), eventDto);
    }

}
