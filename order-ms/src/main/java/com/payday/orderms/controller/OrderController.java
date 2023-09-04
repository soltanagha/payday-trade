package com.payday.orderms.controller;

import com.payday.common.dto.Response;
import com.payday.orderms.dto.OrderDto;
import com.payday.orderms.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Response> createOrder(@Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("order",orderService.createOrder(orderDto)))
                .message("Order placed!")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @GetMapping
    public ResponseEntity<Response> get() {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("orders",orderService.getAll()))
                .message("Order placed!")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

}