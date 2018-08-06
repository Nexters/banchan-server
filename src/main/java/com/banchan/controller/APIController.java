package com.banchan.controller;

import com.banchan.model.entity.Votes;
import com.banchan.model.response.CommonResponse;
import com.banchan.model.vo.QuestionCard;
import com.banchan.repository.QuestionsRepository;
import com.banchan.repository.QuestionsSingularRepository;
import com.banchan.service.question.QuestionDetailsService;
import com.banchan.service.question.QuestionsService;
import com.banchan.service.question.VotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired VotesService votesService;
    @Autowired QuestionsService questionsService;

    // For Test
    @Autowired
    QuestionsSingularRepository questionsSingularRepository;
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    QuestionDetailsService questionDetailsService;

    @RequestMapping(value = "vote", method = RequestMethod.POST)
    public CommonResponse<?> addVote(@RequestBody Votes vote){
        return CommonResponse.success(votesService.add(vote));
    }

    @RequestMapping(value = "questionCard", method = RequestMethod.POST)
    public CommonResponse<?> addQuestions(@Valid @RequestBody QuestionCard questionCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return CommonResponse.FAIL;

        return CommonResponse.success(questionsService.add(questionCard));
    }

    @RequestMapping(value = "voteCount", method = RequestMethod.GET)
    public CommonResponse<?> findVoteCount(){
        return CommonResponse.success(votesService.findVoteCount(questionsRepository.findAll()));
    }

    @RequestMapping(value = "questions", method = RequestMethod.GET)
    public CommonResponse<?> findQuestions(){
        return CommonResponse.success(questionsRepository.findNotSelectedQuestions());
    }

    @RequestMapping(value = "questionDetails", method = RequestMethod.GET)
    public CommonResponse<?> findQuestionDetails(){
        return CommonResponse.success(questionDetailsService.findQuestionDetails(questionsRepository.findAll()));
    }
}
