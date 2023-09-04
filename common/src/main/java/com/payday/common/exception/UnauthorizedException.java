package com.payday.common.exception;

import com.payday.common.exception.util.ExceptionKeyAndMessage;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GeneralException {

    private static final long serialVersionUID = 58432132123511L;

    public UnauthorizedException(Enum<? extends ExceptionKeyAndMessage> keyAndMessage, Exception e) {
        super(keyAndMessage, HttpStatus.UNAUTHORIZED);
    }
}
