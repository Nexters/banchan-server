package com.banchan.controller;

import com.banchan.DTO.QuestionDetails;
import com.banchan.repository.QuestionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired
    QuestionDetailsRepository questionDetailsRepository;

    @RequestMapping("questionDetails")
    public List<QuestionDetails> questionDetails(){
        System.out.println("TEST");
        return questionDetailsRepository.findAll();
    }
}
