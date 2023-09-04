package com.payday.iam.event.consumer;

import com.payday.common.event.consumer.EventConsumer;
import com.payday.common.event.dto.EventDto;
import com.payday.iam.event.dto.EmailVerificationEventDto;
import com.payday.iam.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailVerifiedEventConsumer implements EventConsumer<EmailVerificationEventDto> {

    private final UserServiceImpl userService;

    @Override
    @KafkaListener(topics = "email-verified", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void consume(EventDto<EmailVerificationEventDto> eventDto) {
        userService.emailVerified(eventDto.getBody().getUserDto().getEmail());
        log.info("Email verified: " + eventDto.getBody().getUserDto().getEmail());
    }
}
