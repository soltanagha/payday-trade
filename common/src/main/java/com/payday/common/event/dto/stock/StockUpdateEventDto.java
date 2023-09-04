package com.payday.common.event.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateEventDto {

    private String eventId;
    private List<StockDto> stockDtoList;
    private Instant time;
    private String timezone;
}
