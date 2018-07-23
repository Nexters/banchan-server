package com.banchan.controller;

import com.banchan.domain.question.DetailType;
import com.banchan.repository.QuestionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired
    QuestionDetailsRepository questionDetailsRepository;

    @RequestMapping("questionDetails")
    public DetailType questionDetails(){
        return DetailType.ANSWER_A;
    }
}
