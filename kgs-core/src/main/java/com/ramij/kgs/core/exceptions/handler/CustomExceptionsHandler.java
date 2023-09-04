package com.ramij.kgs.core.exceptions.handler;

import com.ramij.kgs.core.exceptions.InvalidSystemClockExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionsHandler {
    @ExceptionHandler(InvalidSystemClockExceptions.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleResourceNotFoundException(InvalidSystemClockExceptions ex) {
        return ex.getMessage();
    }
}
