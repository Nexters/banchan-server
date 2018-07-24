package com.banchan.controller;

import com.banchan.exception.SomeException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestController
public class SomeExceptionHandler {

    @ExceptionHandler(SomeException.class)
    public ResponseEntity<?> handleSomeException(HttpServletRequest request, SomeException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }

}
