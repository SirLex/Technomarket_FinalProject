package com.technomarket.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.exceptions.BadRequestException;
import com.technomarket.exceptions.NotFoundException;
import com.technomarket.exceptions.VerificationException;
import com.technomarket.model.dtos.ErrorDTO;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.UnexpectedTypeException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler{


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
    public ErrorDTO handleFileSizeLimitExceededException(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("File size is over the limit of 25MB");
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleSizeLimitExceededException(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("File size is over the limit of 25MB");
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleMaxUploadSizeExceededException(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("File size is over the limit of 25MB");
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleValidation(MethodArgumentNotValidException e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("Not valid data. "+e.getBindingResult().getFieldError().getDefaultMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(DateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleDateTimeValidation(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("Not valid DateTime format.");
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleMessageNotReadable(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("Not valid DateTime format. "+e.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnexpectedType(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("Unexpected data. "+e.getMessage());
        dto.setStatus(HttpStatus.UNAUTHORIZED.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleJsonException(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("Problem with parsing json. "+e.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleIllegalStateException(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg("IllegalState: "+e.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setMsg(e.getMessage());
        dto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        dto.setTime(LocalDateTime.now());
        e.printStackTrace();
        return dto;
    }
}
