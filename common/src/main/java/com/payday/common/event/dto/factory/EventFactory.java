package com.payday.common.event.dto.factory;

import com.payday.common.event.dto.EventDto;

public interface EventFactory<T> {

    EventDto<T> createEvent(String name, String topic, T body);
}
