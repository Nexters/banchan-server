package com.banchan.controller;

import com.banchan.model.dto.Vote;
import com.banchan.model.dto.reviews.ReportRequestDto;
import com.banchan.model.response.CommonResponse;
import com.banchan.model.vo.QuestionCard;
import com.banchan.service.question.QuestionsService;
import com.banchan.service.question.VotesService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
public class QuestionController {

    @Autowired VotesService votesService;
    @Autowired QuestionsService questionsService;

    @ApiOperation(value = "투표 등록")
    @RequestMapping(value = "vote", method = RequestMethod.POST)
    public CommonResponse<?> addVote(@RequestBody Vote vote){
        return CommonResponse.success(votesService.add(vote));
    }

    @ApiOperation(value = "질문 등록")
    @RequestMapping(value = "questionCard", method = RequestMethod.POST)
    public CommonResponse<?> addQuestions(@Valid @RequestBody QuestionCard questionCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return CommonResponse.FAIL;

        return CommonResponse.success(questionsService.add(questionCard));
    }

    @ApiOperation(value = "투표 안 한 질문 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "lastOrder", value = "마지막 조회한 질문 번호", required = true, paramType = "path", defaultValue = "-1"),
            @ApiImplicitParam(name = "count", value = "조회할 질문 수", required = true, paramType = "path")
    })
    @RequestMapping(value = "user/{userId}/notVotedQuestions/{lastOrder}/{count}", method = RequestMethod.GET)
    public CommonResponse<?> findNotVotedQuestions(
            @PathVariable("userId") int userId, @PathVariable("lastOrder") int lastOrder, @PathVariable("count") int count) {
        return CommonResponse.success(questionsService.findNotVotedQuestionCard(userId, lastOrder, count));
    }

    @ApiOperation(value = "사용자가 만든 질문 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "page", value = "페이지네이션 번호", required = true, paramType = "path", defaultValue = "0"),
            @ApiImplicitParam(name = "count", value = "조회할 질문 수", required = true, paramType = "path")
    })
    @RequestMapping(value = "user/{userId}/userMadeQuestions/{page}/{count}", method = RequestMethod.GET)
    public CommonResponse<?> userMadeQuestions(
            @PathVariable("userId") int userId, @PathVariable("page") int page, @PathVariable("count") int count) {
        return CommonResponse.success(questionsService.findUserMadeQuestionCard(userId, page, count));
    }

    @ApiOperation(value = "사용자가 투표한 질문 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "page", value = "페이지네이션 번호", required = true, paramType = "path", defaultValue = "0"),
            @ApiImplicitParam(name = "count", value = "조회할 질문 수", required = true, paramType = "path")
    })
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
    public CommonResponse<?> reportQuestion (@RequestBody ReportRequestDto dto) {
        if (questionsService.isOverlap(dto)) {
            return CommonResponse.fail("동일한 유저가 중복 신고");
        }
        return CommonResponse.success(questionsService.saveReport(dto));
    }
}
