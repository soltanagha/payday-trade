package com.payday.common.event.producer;


import com.payday.common.event.dto.EventDto;

public interface EventProducer<T> {

    void produce(EventDto<T> eventDto);
}
