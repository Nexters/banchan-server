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
import io.swagger.annotations.ApiOperation;
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

    @RequestMapping(value = "user/{userId}/notVotedQuestions/{lastOrder}/{count}", method = RequestMethod.GET)
    public CommonResponse<?> findNotVotedQuestions(
            @PathVariable("userId") int userId, @PathVariable("lastOrder") int lastOrder, @PathVariable("count") int count) {
        return CommonResponse.success(questionsService.findNotVotedQuestionCard(userId, lastOrder, count));
    }

    @RequestMapping(value = "user/{userId}/userMadeQuestions/{page}/{count}", method = RequestMethod.GET)
    public CommonResponse<?> userMadeQuestions(
            @PathVariable("userId") int userId, @PathVariable("page") int page, @PathVariable("count") int count) {
        return CommonResponse.success(questionsService.findUserMadeQuestionCard(userId, page, count));
    }

    @RequestMapping(value = "user/{userId}/votedQuestions/{page}/{count}", method = RequestMethod.GET)
    public CommonResponse<?> findVotedQuestions(
            @PathVariable("userId") int userId, @PathVariable("page") int page, @PathVariable("count") int count) {
        return CommonResponse.success(questionsService.findVotedQuestionCard(userId, page, count));
    }

    /**
     * 질문카드 신고 (신고 테이블에 저장하고 해당 질문카드에 신고가 REPORT_MAX_SIZE 이상이면 해당 질문카드 조회 안됨)
     * @param dto | 질문카드 신고용 RequestDto (속성 : userId, questionId)
     */
    @PostMapping("question/report")
    @ApiOperation(value = "질문 신고", notes = "동일한 유저가 동일한 질문을 신고한 경우 fail | reason : isOverlap")
    public CommonResponse<?> reportQuestion (@RequestBody ReportRequestDto dto) {
        if (questionsService.isOverlap(dto)) {
            return CommonResponse.fail("isOverlap");
        }
        return CommonResponse.success(questionsService.saveReport(dto));
    }
}
