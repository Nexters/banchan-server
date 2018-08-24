package com.banchan.service.question;

import com.banchan.model.domain.question.RewardType;
import com.banchan.model.dto.questions.QuestionReportRequestDto;
import com.banchan.model.entity.QuestionCardData;
import com.banchan.model.entity.Questions;
import com.banchan.model.entity.RewardHistory;
import com.banchan.model.exception.QuestionException;
import com.banchan.model.vo.QuestionCard;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.QuestionCardDataRepository;
import com.banchan.repository.QuestionsRepository;
import com.banchan.repository.ReportsRepository;
import com.banchan.repository.RewardHistoryRepository;
import com.google.common.base.Strings;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionsService {

    @Autowired private QuestionsRepository questionsRepository;
    @Autowired private ReportsRepository reportsRepository;
    @Autowired private RewardHistoryRepository rewardHistoryRepository;

    @Autowired private ImageUploader imageUploader;
    @Autowired private QuestionDetailsService questionDetailsService;
    @Autowired private QuestionCardDataRepository questionCardDataRepository;
    @Autowired private Rewarder rewarder;

    @Transactional
    public Questions add(QuestionCard questionCard){

        if(rewardHistoryRepository.sumRewardByUserId(questionCard.getUserId()) < 1.0)
            throw new QuestionException("SpeakerUnderOne");

        // questionCard 필요 조건 명시
        Questions question = questionsRepository.save(
                Questions.builder()
                        .userId(questionCard.getUserId())
                        .randomOrder(new Random().nextInt(Integer.MAX_VALUE))
                        .writeTime(LocalDateTime.now())
                        .decision(questionCard.getDecision())
                        .type(questionCard.getType())
                        .reportState(0)
                        .build());

        questionDetailsService.add(
                question.getId(),
                EntryStream.of(questionCard.getDetail())
                        .filterValues(content -> !Strings.isNullOrEmpty(content))
                        .peek(detail -> detail.setValue(
                                detail.getKey().isImgType() ?
                                        imageUploader.upload(
                                                "" + question.getId() + detail.getKey(),
                                                Base64.getDecoder().decode(detail.getValue()))
                                        : detail.getValue()))
                        .toMap());

        rewardHistoryRepository.save(RewardHistory.builder()
                .userId(questionCard.getUserId())
                .type(RewardType.QUESTION_POST)
                .reward(rewarder.rewardOf(RewardType.QUESTION_POST))
                .createdAt(LocalDateTime.now())
                .build());

        return question;
    }

    public QuestionCard findQuestionCard(Long questionId){
        return this.toQuestionCards(
                this.questionCardDataRepository.findQuestion(questionId)).get(0);
    }

    public List<QuestionCard> findNotVotedQuestionCard(Long userId, int lastOrder, int count){
        if(lastOrder == 0) lastOrder = -1;
        return this.toQuestionCards(
                questionCardDataRepository.findNotVotedQuestions(userId, lastOrder, count));
    }

    public List<QuestionCard> findUserMadeQuestionCard(Long userId, int start, int count){
        return this.toQuestionCards(
                questionCardDataRepository.findUserMadeQuestions(userId, start, count));
    }

    public List<QuestionCard> findVotedQuestionCard(Long userId, int start, int count){
        return this.toQuestionCards(
                questionCardDataRepository.findVotedQuestions(userId, start, count));
    }

    private List<QuestionCard> toQuestionCards(List<QuestionCardData> questionCardDataList){
        if(questionCardDataList == null || questionCardDataList.size() == 0)
            throw new QuestionException("QuestionNotFound");

        Map<Long, QuestionCard> cardMap = EntryStream.of(questionCardDataList.stream()
                .collect(Collectors.groupingBy(QuestionCardData::getId)))
                .mapToKey((id, questionCardDataList1) -> questionCardDataList1.get(0))
                .mapValues(questionCardDataList1 -> questionCardDataList1.stream()
                        .collect(Collectors
                                .toMap(QuestionCardData::getDetailType,
                                        QuestionCardData::getDetailContent)))
                .mapKeyValue((key, detail) -> QuestionCard.builder()
                        .id(key.getId())
                        .order(key.getRandomOrder())
                        .username(Optional.ofNullable(key.getPrefix()).orElse("맛있는")
                                + " " + Optional.ofNullable(key.getPostfix()).orElse("반찬"))
                        .type(key.getType())
                        .decision(key.getDecision())
                        .userId(key.getUserId())
                        .review(key.getReviews())
                        .writeTime(key.getWriteTime())
                        .detail(detail)
                        .vote(new VoteCount(key.getCountA(), key.getCountB()))
                        .tag(QuestionCard.Tag.builder()
                                .NEW(rewarder.checkNew(key.getWriteTime()))
                                .FIRST(rewarder.checkFirst(key.getCountA() + key.getCountB()))
                                .RANDOM(rewarder.checkRandom())
                                .build())
                        .build())
                .collect(Collectors.toMap(QuestionCard::getId, card -> card));

        return  questionCardDataList.stream()
                .map(QuestionCardData::getId)
                .distinct()
                .map(id -> cardMap.get(id))
                .collect(Collectors.toList());

    }

    /**
     * 신고 테이블에 저장. REPORT_MAX_SIZE 이상 신고되면 게시글 신고상태값 변경
     */
    final static int REPORT_MAX_SIZE = 10;
    @Transactional
    public Long saveReport(QuestionReportRequestDto dto) {
        Long reportId = reportsRepository.save(dto.toQuestionReportEntity()).getId();
        if (reportsRepository.countByQuestionId(dto.getQuestionId()) >= REPORT_MAX_SIZE) {
            Questions question = questionsRepository.findById(Long.valueOf(dto.getQuestionId())).get();
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
