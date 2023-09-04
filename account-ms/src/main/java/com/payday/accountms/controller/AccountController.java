package com.payday.accountms.controller;

import com.payday.accountms.service.AccountService;
import com.payday.common.dto.Response;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Response> createOrder(@RequestParam("email") @NotBlank String email) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("account",accountService.getAccountByEmail(email)))
                .message("Account retrieved!")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

}