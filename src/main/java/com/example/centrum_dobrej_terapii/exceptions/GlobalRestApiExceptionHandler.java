package com.example.centrum_dobrej_terapii.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalRestApiExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalRestApiExceptionHandler.class);


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AppUserNotFoundException.class)
    public void handleNotFound(AppUserNotFoundException notFoundException) {
        LOG.error(notFoundException.getMessage());
    }

}
