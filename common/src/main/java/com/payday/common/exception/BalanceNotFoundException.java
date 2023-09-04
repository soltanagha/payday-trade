package com.payday.common.exception;

import com.payday.common.exception.util.ExceptionKeyAndMessage;
import org.springframework.http.HttpStatus;

public class BalanceNotFoundException extends GeneralException{

    private static final long serialVersionUID = 58432132123511L;

    public BalanceNotFoundException(Enum<? extends ExceptionKeyAndMessage> keyAndMessage) {
        super(keyAndMessage, HttpStatus.BAD_REQUEST);
    }

    public BalanceNotFoundException(String message,
                               Enum<? extends ExceptionKeyAndMessage> key) {
        super(message, key, HttpStatus.BAD_REQUEST);
    }

    public BalanceNotFoundException(String message,
                               String key) {
        super(message, key, HttpStatus.BAD_REQUEST);
    }

}
