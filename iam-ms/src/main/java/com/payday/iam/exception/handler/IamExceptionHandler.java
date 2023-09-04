package com.payday.iam.exception.handler;

import com.payday.common.exception.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IamExceptionHandler extends GlobalExceptionHandler {

    public IamExceptionHandler() {
        super("IAM");
    }
}
