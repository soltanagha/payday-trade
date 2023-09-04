package com.payday.emailms.event.consumer;

import com.payday.common.dto.EmailDto;
import com.payday.common.event.consumer.EventConsumer;
import com.payday.common.event.dto.EventDto;
import com.payday.emailms.event.dto.EmailVerificationEventDto;
import com.payday.emailms.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationEventConsumer implements EventConsumer<EmailVerificationEventDto> {

    private final EmailServiceImpl emailService;

    @Override
    @KafkaListener(topics = "email-verification", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void consume(EventDto<EmailVerificationEventDto> eventDto) {
        emailService.sendActivationLink(EmailDto.builder().to(eventDto.getBody().getUserDto().getEmail()).build());
        log.info("Consumed prices: " + eventDto.getBody().getUserDto().getEmail());
    }
}
