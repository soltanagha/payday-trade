package com.payday.emailms.controller;

import com.payday.common.dto.Response;
import com.payday.emailms.service.EmailServiceImpl;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailServiceImpl emailService;

    @GetMapping("/activate")
    public ResponseEntity<Response> activateUser(@RequestParam("activation_key") @NotBlank String activationKey) {
        emailService.activateUser(activationKey);
        return ResponseEntity.ok(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("activation-status","User activation done!"))
                        .message("User activated!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                .build());
    }

}
