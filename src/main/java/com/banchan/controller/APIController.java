package com.banchan.controller;

import com.banchan.dto.RawQuestionCard;
import com.banchan.repository.QuestionDetailsRepository;
import com.banchan.repository.QuestionsRepository;
import com.banchan.service.question.AwsS3Service;
import com.banchan.service.question.QuestionCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired QuestionCardService questionCardService;
    @Autowired QuestionDetailsRepository questionDetailsRepository;
    @Autowired QuestionsRepository questionsRepository;
    @Autowired AwsS3Service awsS3Service;

    @RequestMapping(value = "questionCards", method = RequestMethod.GET)
    public ResponseEntity<?> questionCards(){
        return ResponseEntity.ok(questionCardService.questionCards());
    }

    @RequestMapping("RawQuestionCards")
    public List<RawQuestionCard> RawQuestionCards(){
        return questionsRepository.findAllRawQuestionCard();
    }

    @RequestMapping("test")
    public String helloWorld(){
        return "Hello World";
    }

    @RequestMapping(value = "imgUpload", method = RequestMethod.POST)
    public ResponseEntity<?> imgUpload(@RequestBody MultipartFile multipartFile){
        awsS3Service.upload(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body("img upload success");
    }
}
