package com.technomarket.controller;

import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception exception) {

        //TODO
        return null;
    }
}
