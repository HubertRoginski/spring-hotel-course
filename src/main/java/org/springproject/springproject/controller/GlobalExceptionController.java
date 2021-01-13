package org.springproject.springproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springproject.springproject.exception.NoSuchEmployeeId;
import org.springproject.springproject.exception.WrongPageException;
import org.springproject.springproject.model.Error;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongPageException.class)
    public Error pageExceptionHandler(WrongPageException wrongPageException){
        return new Error(wrongPageException.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchEmployeeId.class)
    public Error personnelIdExceptionHandler(NoSuchEmployeeId noSuchEmployeeId){
        return new Error(noSuchEmployeeId.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Error wrongRequestParamTypeExceptionHandler(MethodArgumentTypeMismatchException notValidException){
        return new Error(StringUtils.capitalize(notValidException.getName()) + " parameter has value = '"+ Objects.requireNonNull(notValidException.getValue()).toString()+"' of type "
                +notValidException.getValue().getClass()+". An invalid data type was specified. Required parameter data type is "
                + Objects.requireNonNull(notValidException.getRequiredType()).getName()+".");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException exception) {
        return error(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }
}
