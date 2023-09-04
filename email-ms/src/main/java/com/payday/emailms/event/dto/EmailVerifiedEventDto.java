package com.payday.emailms.event.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerifiedEventDto {

    @NotBlank(message = "Event ID is required")
    private String eventId;

    @Valid
    @NotNull(message = "User details are required")
    private UserDto userDto;

    @NotNull(message = "Time is required")
    private Instant time;

    @NotBlank(message = "Timezone is required")
    private String timezone;
}
