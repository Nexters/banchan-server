package com.banchan.controller;

import com.banchan.domain.question.DetailType;
import com.banchan.dto.QuestionCardData;
import com.banchan.exception.SomeException;
import com.banchan.repository.QuestionDetailsRepository;
import com.banchan.repository.QuestionsRepository;
import com.banchan.service.question.AwsS3ServiceImpl;
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
import java.util.Map;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired QuestionCardService questionCardService;
    @Autowired QuestionDetailsRepository questionDetailsRepository;
    @Autowired QuestionsRepository questionsRepository;
    @Autowired
    AwsS3ServiceImpl awsS3Service;

    @RequestMapping(value = "questionCards", method = RequestMethod.GET)
    public ResponseEntity<?> questionCards(){
        return ResponseEntity.ok(questionCardService.questionCards());
    }

    @RequestMapping("QuestionCardsData")
    public List<QuestionCardData> QuestionCardsData(){
        return questionsRepository.findAllQuestionCardData();
    }

    @RequestMapping("test")
    public String helloWorld(){
        return "Hello World";
    }

    @RequestMapping(value = "imgUpload", method = RequestMethod.POST)
    public ResponseEntity<?> imgUpload(@RequestBody MultipartFile multipartFile){

        awsS3Service.upload(DetailType.IMG_A, multipartFile);

        /*
        for(String key : request.keySet()){
            if(! (request.get(key) instanceof MultipartFile))
                throw new SomeException();
        }
        */
        // awsS3Service.upload(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body("img upload success");
    }
}
