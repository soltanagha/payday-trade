package com.payday.common.exception.factory;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface ExceptionFactory {

    Map<String, Object> ofType(HttpStatus httpStatus, String error, String key, String path);
}
