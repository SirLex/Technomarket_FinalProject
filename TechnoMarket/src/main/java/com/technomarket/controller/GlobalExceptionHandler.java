package com.technomarket.controller;

import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.exceptions.BadRequestException;
import com.technomarket.exceptions.NotFoundException;
import com.technomarket.exceptions.VerificationException;
import com.technomarket.model.dtos.ErrorDTO;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg(e.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg(e.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        return dto;
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleAuthorization(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg(e.getMessage());
        dto.setStatus(HttpStatus.UNAUTHORIZED.value());
        dto.setTime(LocalDateTime.now());
        return dto;
    }

    @ExceptionHandler(VerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleVerification(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg(e.getMessage());
        dto.setStatus(HttpStatus.UNAUTHORIZED.value());
        dto.setTime(LocalDateTime.now());
        return dto;
    }
    @ExceptionHandler(FileSizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO fileSizeOverLimit(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("File size is over the limit of 25MB");
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }
}
