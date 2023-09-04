package com.payday.emailms.handler;

import com.payday.common.exception.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmailExceptionHandler extends GlobalExceptionHandler {

    public EmailExceptionHandler() {
        super("EMAIL");
    }
}
