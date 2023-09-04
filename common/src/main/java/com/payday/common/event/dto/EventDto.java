package com.payday.common.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto<T> {
    private String name;
    private String topic;
    private Instant timestamp;
    private T body;
}
