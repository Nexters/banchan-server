package com.banchan.controller;

import com.banchan.config.annotation.BanchanAuth;
import com.banchan.model.dto.Vote;
import com.banchan.model.dto.questions.QuestionReportRequestDto;
import com.banchan.model.entity.Questions;
import com.banchan.model.response.CommonResponse;
import com.banchan.model.vo.QuestionCard;
import com.banchan.repository.QuestionCardDataRepository;
import com.banchan.service.question.QuestionsService;
import com.banchan.service.question.VotesService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
public class QuestionController {

    @Autowired VotesService votesService;
    @Autowired QuestionsService questionsService;
    @Autowired
    QuestionCardDataRepository questionsRepository;

    @GetMapping(value = "test")
    public List<QuestionCard> test(){
        return questionsService.findNotVotedQuestionCard2();
    }

    @GetMapping(value = "test2")
    public List<QuestionCard> test2(){
        return questionsService.findVotedQuestionCard2();
    }

    @GetMapping(value = "test3")
    public List<QuestionCard> test3(){
        return questionsService.findUserMadeQuestionCard2();
    }

    @ApiOperation(value = "투표 등록",
            notes = "answer: A or B - 무엇에 투표를 하였는지 / " +
                    "questionId - 어떤 질문에 투표를 하였는지 / " +
                    "random: true or false - 이 질문에 랜덤 딱지가 붙었는지 / " +
                    "userId - 어떤 유저가 투표했는지 / " +
                    "응답: 리워드 (단위 %) / " +
                    "유저가 질문 작성자일 경우 질문에 최종 결정을 하게 됨 / " +
                    "응답: 영향 받은 사람 수 (현재는 -1이 리턴)"
    )
    @BanchanAuth
    @RequestMapping(value = "vote", method = RequestMethod.POST)
    public CommonResponse<Double> addVote(@RequestBody Vote vote){
        return CommonResponse.success(votesService.add(vote));
    }

    @ApiOperation(value = "질문 등록",
            notes = "detail: 질문 상세 요소 e.g.) " +
                    "\"detail\": {\n" +
                    "  \t\"TXT_Q\": \"너희는 뭐가 좋아?\",\n" +
                    "  \t\"TXT_A\": \"하얀 가방\",\n" +
                    "  \t\"TXT_B\": \"파란 가방\"\n" +
                    "}" +
                    " / userId - 어떤 유저가 투표했는지"
    )
    @BanchanAuth
    @RequestMapping(value = "questionCard", method = RequestMethod.POST)
    public CommonResponse<Questions> addQuestions(@Valid @RequestBody QuestionCard questionCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return CommonResponse.FAIL;

        return CommonResponse.success(questionsService.add(questionCard));
    }

    @ApiOperation(value = "투표 안 한 질문 조회",
            notes = "마지막 조회한 질문의 order 를 보고 다음 질문을 조회 / " +
                    "사용자가 투표하지 않고 작성하지 않았으며 신고 받아 삭제되지 않은 질문을 조회"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "lastOrder", value = "마지막 조회한 질문의 order", required = true, paramType = "path", defaultValue = "0"),
            @ApiImplicitParam(name = "count", value = "조회할 질문 수", required = true, paramType = "path")
    })
    @BanchanAuth
    @RequestMapping(value = "user/{userId}/notVotedQuestions/{lastOrder}/{count}", method = RequestMethod.GET)
    public CommonResponse<List<?>> findNotVotedQuestions(
            @PathVariable("userId") int userId, @PathVariable("lastOrder") int lastOrder, @PathVariable("count") int count) {
        return CommonResponse.success(questionsService.findNotVotedQuestionCard(userId, lastOrder - 1, count));
    }

    @ApiOperation(value = "사용자가 만든 질문 조회",
            notes = "사용자가 작성한 질문들을 조회 / " +
                    "페이지네이션으로 조회되기 때문에 페이지 번호가 필요. 0부터 시작"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "page", value = "페이지네이션 번호", required = true, paramType = "path", defaultValue = "0"),
            @ApiImplicitParam(name = "count", value = "조회할 질문 수", required = true, paramType = "path")
    })
    @BanchanAuth
    @RequestMapping(value = "user/{userId}/userMadeQuestions/{page}/{count}", method = RequestMethod.GET)
    public CommonResponse<List<?>> userMadeQuestions(
            @PathVariable("userId") int userId, @PathVariable("page") int page, @PathVariable("count") int count) {
        return CommonResponse.success(questionsService.findUserMadeQuestionCard(userId, page, count));
    }

    @ApiOperation(value = "사용자가 투표한 질문 조회",
            notes = "사용자가 투표한 질문들을 조회 / " +
                    "페이지네이션으로 조회되기 때문에 페이지 번호가 필요. 0부터 시작"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "page", value = "페이지네이션 번호", required = true, paramType = "path", defaultValue = "0"),
            @ApiImplicitParam(name = "count", value = "조회할 질문 수", required = true, paramType = "path")
    })
    @BanchanAuth
    @RequestMapping(value = "user/{userId}/votedQuestions/{page}/{count}", method = RequestMethod.GET)
    public CommonResponse<List<?>> findVotedQuestions(
            @PathVariable("userId") int userId, @PathVariable("page") int page, @PathVariable("count") int count) {
        return CommonResponse.success(questionsService.findVotedQuestionCard(userId, page, count));
    }

    /**
     * 질문카드 신고 (신고 테이블에 저장하고 해당 질문카드에 신고가 REPORT_MAX_SIZE 이상이면 해당 질문카드 조회 안됨)
     * @param dto | 질문카드 신고용 RequestDto (속성 : userId, questionId)
     */
    @PostMapping("question/report")
    @ApiOperation(value = "질문 신고", notes = "성공 시 신고 고유 id 반환 // " +
            "동일한 유저가 동일한 질문을 신고한 경우 fail | reason : isOverlap")
    @BanchanAuth
    public CommonResponse reportQuestion (@RequestBody QuestionReportRequestDto dto) {
        if (questionsService.isOverlap(dto)) {
            return CommonResponse.fail("isOverlap");
        }
        return CommonResponse.success(questionsService.saveReport(dto));
    }
}
