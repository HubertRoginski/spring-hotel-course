package org.springproject.springproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springproject.springproject.exception.NoSuchPersonnelId;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Error;

@RestControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongPageException.class)
    public Error pageExceptionHandler(WrongPageException wrongPageException){
        return new Error(wrongPageException.getMessage());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchPersonnelId.class)
    public Error personnelIdExceptionHandler(NoSuchPersonnelId noSuchPersonnelId){
        return new Error(noSuchPersonnelId.getMessage());
    }
}
