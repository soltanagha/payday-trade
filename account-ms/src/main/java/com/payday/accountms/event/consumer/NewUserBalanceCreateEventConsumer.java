package com.payday.accountms.event.consumer;

import com.payday.accountms.dto.AccountDto;
import com.payday.accountms.event.dto.NewUserBalanceCreateEventDto;
import com.payday.accountms.service.AccountService;
import com.payday.common.event.consumer.EventConsumer;
import com.payday.common.event.dto.EventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewUserBalanceCreateEventConsumer implements EventConsumer<NewUserBalanceCreateEventDto> {

    private final AccountService accountService;

    @Override
    @KafkaListener(topics = "new-user-balance", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void consume(EventDto<NewUserBalanceCreateEventDto> eventDto) {

        AccountDto accountDto = AccountDto.builder()
                .email(eventDto.getBody().getUserDto().getEmail()).build();
        accountService.createAccount(accountDto);
        log.info("Consumed new user balance create: " + eventDto.getBody().getUserDto().toString());
    }
}
