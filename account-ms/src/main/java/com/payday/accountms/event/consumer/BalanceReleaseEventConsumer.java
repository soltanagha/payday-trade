package com.payday.accountms.event.consumer;

import com.payday.accountms.service.BalanceReservedService;
import com.payday.common.event.consumer.EventConsumer;
import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.account.BalanceReleaseEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceReleaseEventConsumer implements EventConsumer<BalanceReleaseEventDto> {

    private final BalanceReservedService balanceReservedService;

    @Override
    @KafkaListener(topics = "balance-release", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void consume(EventDto<BalanceReleaseEventDto> eventDto) {

        var payload = eventDto.getBody().getBalanceReleaseDto();
        balanceReservedService.balanceReleased(payload.orderId(),payload.toSymbol(),payload.price());
        log.info("Consumed balance released: " + payload.toString());
    }
}
