package com.payday.orderms.event.dto;

import com.payday.orderms.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDto {

    private String eventId;
    private OrderDto orderDto;
    private Instant time;
    private String timezone;
}
