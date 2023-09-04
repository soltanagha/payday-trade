package com.payday.common.exception;

import com.payday.common.exception.util.ExceptionKeyAndMessage;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends GeneralException {

    private static final long serialVersionUID = 58432132123512L;

    public ForbiddenException(Enum<? extends ExceptionKeyAndMessage> keyAndMessage) {
        super(keyAndMessage, HttpStatus.FORBIDDEN);
    }

}
