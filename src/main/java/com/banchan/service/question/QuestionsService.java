package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.dto.questions.QuestionReportRequestDto;
import com.banchan.model.dto.reviews.ReviewReportRequestDto;
import com.banchan.model.entity.Questions;
import com.banchan.model.exception.QuestionException;
import com.banchan.model.vo.QuestionCard;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.QuestionsRepository;
import com.banchan.repository.ReportsRepository;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class QuestionsService {

    @Autowired private QuestionsRepository questionsRepository;
    @Autowired private ReportsRepository reportsRepository;

    @Autowired private ImageUploader imageUploader;
    @Autowired private QuestionDetailsService questionDetailsService;
    @Autowired private VotesService votesService;

    @Transactional
    public Questions add(QuestionCard questionCard){

        // questionCard 필요 조건 명시

        Questions question = questionsRepository.save(
                Questions.builder()
                        .userId(questionCard.getUserId())
                        .randomOrder(new Random().nextInt(Integer.MAX_VALUE))
                        .writeTime(LocalDateTime.now())
                        .reportState(0)
                        .build());

        questionDetailsService.add(
                question.getId(),
                EntryStream.of(questionCard.getDetail())
                        .peek(detail -> detail.setValue(
                                detail.getKey().isImgType() ?
                                        imageUploader.upload(
                                                "" + question.getId() + detail.getKey(),
                                                Base64.getDecoder().decode(detail.getValue()))
                                        : detail.getValue()))
                        .toMap());

        return question;
    }

    public List<QuestionCard> findVotedQuestionCard(int userId, int page, int count){
        return this.findQuestionCardByQuestions(
                questionsRepository.findVotedQuestions(userId, PageRequest.of(page, count))
                .getContent());
    }

    public List<QuestionCard> findUserMadeQuestionCard(int userId, int page, int count){
        return this.findQuestionCardByQuestions(
                questionsRepository.findAllByUserIdAndReportStateOrderByDecisionAscIdDesc(userId, 0,  PageRequest.of(page, count))
                        .getContent());
    }

    public List<QuestionCard> findNotVotedQuestionCard(int userId, int lastOrder, int count){

        List<QuestionCard> result = findQuestionCardByQuestions(
                questionsRepository.findNotVotedQuestions(userId, lastOrder, count));
        Collections.shuffle(result);

        return result;
    }

    private List<QuestionCard> findQuestionCardByQuestions(List<Questions> questions){
        if(questions == null || questions.size() == 0) throw new QuestionException("QuestionNotFound");

        List<Integer> questionIds = questions.stream().map(Questions::getId).collect(Collectors.toList());

        try {

            CompletableFuture<Map<Integer, Map<DetailType, String>>> cfDetailMap =
                    questionDetailsService.findQuestionDetails(questionIds);

            CompletableFuture<Map<Integer, VoteCount>> cfVoteCountMap =
                    votesService.findVoteCount(questionIds);

            return CompletableFuture.allOf(cfDetailMap, cfVoteCountMap)
                    .thenApply(ignoredVoid ->
                            this.toQuestionCards(questions, cfDetailMap.join(), cfVoteCountMap.join()))
                    .get();

        } catch (InterruptedException e) { throw new RuntimeException(e);
        } catch (ExecutionException e) { throw new RuntimeException(e); }
    }

    private List<QuestionCard> toQuestionCards(
            List<Questions> questions,
            Map<Integer, Map<DetailType, String>> detailMap,
            Map<Integer, VoteCount> voteCountMap){

        return questions.stream()
                .map(question -> QuestionCard.builder()
                        .id(question.getId())
                        .order(question.getRandomOrder())
                        .userId(question.getUserId())
                        .detail(detailMap.get(question.getId()))
                        .vote(voteCountMap.get(question.getId()))
                        .writeTime(question.getWriteTime())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 신고 테이블에 저장. REPORT_MAX_SIZE 이상 신고되면 게시글 신고상태값 변경
     */
    final static int REPORT_MAX_SIZE = 10;
    @Transactional
    public Integer saveReport(QuestionReportRequestDto dto) {
        Integer reportId = reportsRepository.save(dto.toQuestionReportEntity()).getId();
        if (reportsRepository.countByQuestionId(dto.getQuestionId()) >= REPORT_MAX_SIZE) {
            Questions question = questionsRepository.findById(dto.getQuestionId()).get();
            question.report();
            questionsRepository.save(question);
        }
        return reportId;
    }

    /**
     * 동일한 사용자가 신고한것인지 중복 체크
     */
    public boolean isOverlap(QuestionReportRequestDto dto) {
        return reportsRepository.countByUserIdAndQuestionId(dto.getUserId(), dto.getQuestionId()) >= 1;
    }
}
