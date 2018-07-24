package com.banchan.controller;

import com.banchan.domain.question.QuestionCard;
import com.banchan.dto.RawQuestionCard;
import com.banchan.repository.QuestionDetailsRepository;
import com.banchan.repository.QuestionsRepository;
import com.banchan.service.question.QuestionCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired QuestionCardService questionCardService;
    @Autowired QuestionDetailsRepository questionDetailsRepository;
    @Autowired QuestionsRepository questionsRepository;

    @RequestMapping("questionCards")
    public List<QuestionCard> questionCards(){
        return questionCardService.questionCards();
    }

    @RequestMapping("RawQuestionCards")
    public List<RawQuestionCard> RawQuestionCards(){
        return questionsRepository.findAllRawQuestionCard();
    }

    @RequestMapping("test")
    public String helloWorld(){
        return "Hello World!";
    }
}
