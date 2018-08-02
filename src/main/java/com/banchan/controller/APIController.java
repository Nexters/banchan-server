package com.banchan.controller;

import com.banchan.service.question.QuestionCardService;
import com.banchan.service.question.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired RawObjectService rawObjectService;
    @Autowired QuestionsService questionsService;
    @Autowired QuestionCardService questionCardService;

    @RequestMapping(value = "questionCards", method = RequestMethod.GET)
    public ResponseEntity<?> questionCards(){
        return ResponseEntity.ok(questionCardService.questionCards());
    }

    @RequestMapping(value = "question", method = RequestMethod.POST)
    public ResponseEntity<?> addQuestion(@RequestBody RawQuestion rawQuestion){
        return ResponseEntity.ok(rawObjectService.add(rawQuestion));
    }

    @RequestMapping("test")
    public String helloWorld(){
        return "Hello World";
    }

    @RequestMapping(value = "imgUpload", method = RequestMethod.POST)
    public ResponseEntity<?> imgUpload(@RequestBody MultipartFile multipartFile){

        return ResponseEntity.status(HttpStatus.OK).body("img upload success");
    }
}
