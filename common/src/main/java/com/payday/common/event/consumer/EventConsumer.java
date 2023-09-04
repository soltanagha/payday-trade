package com.payday.common.event.consumer;


import com.payday.common.event.dto.EventDto;

public interface EventConsumer<T> {

    void consume(EventDto<T> eventDto);
}
