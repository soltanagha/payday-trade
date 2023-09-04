package com.payday.common.exception.handler;


import com.payday.common.exception.*;
import com.payday.common.exception.constants.HttpResponseConstants;
import com.payday.common.exception.util.ConstraintsViolationError;
import com.payday.common.exception.util.ExceptionCodes;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.net.ConnectException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Slf4j
public class GlobalExceptionHandler extends DefaultErrorAttributes {
    private final String preKey;
    private static final String ARGUMENT_VALIDATION_FAILED = "Argument validation failed";
    private static final String VALIDATION_FAILED = "Validation failed";

    public GlobalExceptionHandler(String preKey) {
        this.preKey = preKey;
    }

    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<Map<String, Object>> handle(GeneralException ex,
                                                            WebRequest request) {
        log.trace("General Exception {}", ex.getMessage());
        return ofType(request, ex.getHttpStatus(), ex.getMessage(), ex.getExceptionKey());
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Map<String, Object>> handle(NotFoundException ex,
                                                            WebRequest request) {
        log.trace("Resource not found {}", ex.getMessage());
        return ofType(request, ex.getHttpStatus(), ex.getMessage(), ex.getExceptionKey());
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Map<String, Object>> handle(BadRequestException ex,
                                                            WebRequest request) {
        log.trace("Bad request {}", ex.getMessage());
        return ofType(request, ex.getHttpStatus(), ex.getMessage(), ex.getExceptionKey());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<Map<String, Object>> handle(UnauthorizedException ex,
                                                            WebRequest request) {
        log.trace("Unauthorized {}", ex.getMessage());
        return ofType(request, ex.getHttpStatus(), ex.getMessage(), ex.getExceptionKey());
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<Map<String, Object>> handle(ForbiddenException ex,
                                                            WebRequest request) {
        log.trace("Forbidden {}", ex.getMessage());
        return ofType(request, ex.getHttpStatus(), ex.getMessage(), ex.getExceptionKey());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public final ResponseEntity<Map<String, Object>> handle(MaxUploadSizeExceededException ex,
                                                            WebRequest request) {
        log.trace("Maximum upload size exceed: {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage(),
                ExceptionCodes.MAX_UPLOAD_SIZE_EXCEEDED.getExceptionKey());
    }

    @ExceptionHandler(ConnectException.class)
    public final ResponseEntity<Map<String, Object>> handle(ConnectException ex,
                                                            WebRequest request) {
        log.trace("Timeout {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage(),
                ExceptionCodes.CONNECT_EXCEPTION.getExceptionKey());
    }


    @ExceptionHandler(ServletException.class)
    public final ResponseEntity<Map<String, Object>> handle(ServletException ex,
                                                            WebRequest request) {
        log.trace("Resource not found {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage(),
                ExceptionCodes.SERVLET_EXCEPTION.getExceptionKey());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Map<String, Object>> handle(MethodArgumentTypeMismatchException ex,
                                                            WebRequest request) {
        log.trace("Method  arguments are not valid {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ARGUMENT_VALIDATION_FAILED,
                ExceptionCodes.METHOD_ARGUMENT_TYPE_MISMATCH.getExceptionKey());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Map<String, Object>> handle(ConstraintViolationException ex,
                                                            WebRequest request) {
        log.trace("Method  arguments are not valid {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, VALIDATION_FAILED,
                ExceptionCodes.METHOD_ARGUMENT_TYPE_MISMATCH.getExceptionKey());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        List<ConstraintsViolationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ConstraintsViolationError(error.getField(), error.getDefaultMessage())).toList();
        return ofType(request, HttpStatus.BAD_REQUEST, ARGUMENT_VALIDATION_FAILED,
                ExceptionCodes.METHOD_ARGUMENT_NOT_VALID.getExceptionKey(), validationErrors);
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public final ResponseEntity<Map<String, Object>> handle(Throwable throwable,
                                                            WebRequest request) {
        log.trace(throwable.getMessage());
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", "500");
    }

    public ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
                                                         String exceptionKey) {
        return ofType(request, status, message, exceptionKey, Collections.EMPTY_LIST);
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
                                                       String exceptionKey, List<?> validationErrors) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(HttpResponseConstants.STATUS, status.value());
        attributes.put(HttpResponseConstants.ERROR, status.getReasonPhrase());
        attributes.put(HttpResponseConstants.MESSAGE, message);
        attributes.put(HttpResponseConstants.ERRORS, validationErrors);
        attributes.put(HttpResponseConstants.KEY, preKey + exceptionKey);
        attributes.put(HttpResponseConstants.PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }
}
