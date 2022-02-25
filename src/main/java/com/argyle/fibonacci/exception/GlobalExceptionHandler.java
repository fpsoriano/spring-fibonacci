package com.argyle.fibonacci.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_ERROR_HANDLING_MESSAGE = "Handling {}: {}";
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        final ErrorCodes errorCode = ErrorCodes.MALFORMED_REQUEST;
        logger.error(DEFAULT_ERROR_HANDLING_MESSAGE, ex.getClass().getSimpleName(), errorCode, ex);
        final List<String> error = List.of(ex.getParameterName() + " : " + ex.getMessage());
        return new Error(errorCode, error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        final ErrorCodes errorCode = ErrorCodes.MALFORMED_REQUEST;
        logger.error(DEFAULT_ERROR_HANDLING_MESSAGE, ex.getClass().getSimpleName(), errorCode, ex);
        final List<String> error = List.of(ex.getMessage());
        return new Error(errorCode, error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected Error processException(final Exception ex) {
        final ErrorCodes errorCode = ErrorCodes.INTERNAL_SERVER_ERROR;
        logger.error(DEFAULT_ERROR_HANDLING_MESSAGE, ex.getClass().getSimpleName(), errorCode, ex);
        return new Error(errorCode);
    }
}
