package com.payday.orderms.event.consumer;

import com.payday.common.event.consumer.EventConsumer;
import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.account.BalanceReservedEventDto;
import com.payday.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderBalanceReservedEventConsumer implements EventConsumer<BalanceReservedEventDto> {

    private final OrderService orderService;

    @Override
    @KafkaListener(topics = "balance-reserved", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void consume(EventDto<BalanceReservedEventDto> eventDto) {
        orderService.orderBalanceReserved(eventDto.getBody().getBalanceReserveDto().orderId());
        log.info("Consumed order balance reserved id: " + eventDto.getBody().toString());
    }
}
