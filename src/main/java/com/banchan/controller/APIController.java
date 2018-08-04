package com.banchan.controller;

import com.banchan.service.question.QuestionCardService;
import com.banchan.service.question.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired QuestionsService questionsService;
    @Autowired QuestionCardService questionCardService;

}
