package com.banchan.controller;

import com.banchan.model.exception.QuestionException;
import com.banchan.model.response.CommonResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestController
public class QuestionExceptionHandler {

    @ExceptionHandler(QuestionException.class)
    public CommonResponse<?> handleSomeException(HttpServletRequest request, QuestionException ex){
        return CommonResponse.fail(ex.getMessage());
    }

}
