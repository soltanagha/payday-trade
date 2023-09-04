package com.payday.accountms.handler;

import com.payday.common.exception.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountExceptionHandler extends GlobalExceptionHandler {

    public AccountExceptionHandler() {
        super("ACCOUNT");
    }
}
