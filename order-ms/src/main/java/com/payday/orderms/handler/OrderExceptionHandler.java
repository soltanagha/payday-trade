package com.payday.orderms.handler;

import com.payday.common.exception.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler extends GlobalExceptionHandler {

    public OrderExceptionHandler() {
        super("ORDER");
    }
}
