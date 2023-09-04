package com.payday.common.event.dto.factory.impl;

import com.payday.common.event.dto.EventDto;
import com.payday.common.event.dto.factory.EventFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EventFactoryImpl<T> implements EventFactory<T> {

    @Override
    public EventDto<T> createEvent(String name, String topic, T body) {
        return EventDto.<T>builder()
                .body(body)
                .timestamp(Instant.now())
                .topic(topic)
                .name(name)
                .build();
    }
}
