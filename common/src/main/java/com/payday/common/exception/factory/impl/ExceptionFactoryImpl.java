package com.payday.common.exception.factory.impl;

import com.payday.common.exception.constants.HttpResponseConstants;
import com.payday.common.exception.factory.ExceptionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionFactoryImpl implements ExceptionFactory {

    @Override
    public Map<String, Object> ofType(HttpStatus httpStatus, String error, String key, String path) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(HttpResponseConstants.STATUS, httpStatus.value());
        attributes.put(HttpResponseConstants.ERROR, httpStatus.getReasonPhrase());
        attributes.put(HttpResponseConstants.MESSAGE, error);
        attributes.put(HttpResponseConstants.KEY, key);
        attributes.put(HttpResponseConstants.PATH, path);
        return attributes;
    }
}
