package com.payday.common.event.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceReleaseEventDto {

    private String eventId;
    private BalanceReleaseDto balanceReleaseDto;
    private Instant time;
    private String timezone;
}
