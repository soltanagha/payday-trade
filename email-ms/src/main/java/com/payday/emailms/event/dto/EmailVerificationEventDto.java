package com.payday.emailms.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationEventDto {

    private String eventId;
    private UserDto userDto;
    private Instant time;
    private String timezone;
}
