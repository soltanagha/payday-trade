package com.payday.stockms.handler;

import com.payday.common.exception.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockExceptionHandler extends GlobalExceptionHandler {

    public StockExceptionHandler() {
        super("STOCK");
    }
}
