package com.payday.common.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadRequestExceptionTest {

    @Test
    public void testConstructorWithMessageAndStringKey() {
        String message = "Another test message";
        String key = "Test key";
        BadRequestException exception = new BadRequestException(message, key);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(message, exception.getMessage());
    }
}
