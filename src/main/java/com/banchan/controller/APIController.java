package com.banchan.controller;

import com.banchan.model.dto.reviews.ReportRequestDto;
import com.banchan.model.entity.Questions;
import com.banchan.model.dto.Vote;
import com.banchan.model.response.CommonResponse;
import com.banchan.model.vo.QuestionCard;
import com.banchan.repository.QuestionsRepository;
import com.banchan.service.question.QuestionDetailsService;
import com.banchan.service.question.QuestionsService;
import com.banchan.service.question.VotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired VotesService votesService;
    @Autowired QuestionsService questionsService;

    // For Test
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    QuestionDetailsService questionDetailsService;

    @RequestMapping(value = "vote", method = RequestMethod.POST)
    public CommonResponse<?> addVote(@RequestBody Vote vote){
        return CommonResponse.success(votesService.add(vote));
    }

    @RequestMapping(value = "questionCard", method = RequestMethod.POST)
    public CommonResponse<?> addQuestions(@Valid @RequestBody QuestionCard questionCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return CommonResponse.FAIL;

        return CommonResponse.success(questionsService.add(questionCard));
    }

    @RequestMapping(value = "voteCount", method = RequestMethod.GET)
    public CommonResponse<?> findVoteCount() throws ExecutionException, InterruptedException {
        return CommonResponse.success(votesService.findVoteCount(questionsRepository.findAll().stream()
                .map(Questions::getId).collect(Collectors.toList())).get());
    }

    @RequestMapping(value = "questions", method = RequestMethod.GET)
    public CommonResponse<?> findQuestions(){
        return CommonResponse.success(questionsRepository.findNotVotedQuestions(-1, 3, 50));
    }

    @RequestMapping(value = "questionDetails", method = RequestMethod.GET)
    public CommonResponse<?> findQuestionDetails() throws ExecutionException, InterruptedException {
        return CommonResponse.success(questionDetailsService.findQuestionDetails(questionsRepository.findAll().stream()
                .map(Questions::getId).collect(Collectors.toList())).get());
    }

    @RequestMapping(value = "notVotedQuestions", method = RequestMethod.GET)
    public CommonResponse<?> findNotVotedQuestions() {
        return CommonResponse.success(questionsService.findNotVotedQuestionCard(3, 0, 50));
    }

    @RequestMapping(value = "userMadeQuestions", method = RequestMethod.GET)
    public CommonResponse<?> userMadeQuestions() {
        return CommonResponse.success(questionsService.findUserMadeQuestionCard(1, 1, 10));
    }

    @RequestMapping(value = "votedQuestions", method = RequestMethod.GET)
    public CommonResponse<?> findVotedQuestions() {
        return CommonResponse.success(questionsService.findVotedQuestionCard(2, 0, 10));
    }

    /**
     * 질문카드 신고 (신고 테이블에 저장하고 해당 질문카드에 신고가 REPORT_MAX_SIZE 이상이면 해당 질문카드 조회 안됨)
     * @param dto | 질문카드 신고용 RequestDto (속성 : userId, questionId)
     */
    @PostMapping("question/report")
    public CommonResponse<?> reportQuestion (@RequestBody ReportRequestDto dto) {
        if (questionsService.isOverlap(dto)) {
            return CommonResponse.fail("동일한 유저가 중복 신고");
        }
        return CommonResponse.success(questionsService.saveReport(dto));
    }
}
