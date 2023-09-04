package com.payday.iam.event.dto;

import com.payday.common.event.dto.iam.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserBalanceCreateEventDto {

    private String eventId;
    private UserDto userDto;
    private Instant time;
    private String timezone;
}
