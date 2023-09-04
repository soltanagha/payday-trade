package com.payday.accountms.event.consumer;

import com.payday.accountms.service.BalanceService;
import com.payday.common.event.consumer.EventConsumer;
import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.account.BalanceReserveEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceReserveEventConsumer implements EventConsumer<BalanceReserveEventDto> {

    private final BalanceService balanceService;

    @Override
    @KafkaListener(topics = "balance-reserve", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void consume(EventDto<BalanceReserveEventDto> eventDto) {

        var payload = eventDto.getBody().getBalanceReserveDto();
        balanceService.reserveBalance(payload.orderId(),payload.email(),payload.symbol(),payload.amount());
        log.info("Consumed balance sufficient: " + payload.toString());
    }
}
