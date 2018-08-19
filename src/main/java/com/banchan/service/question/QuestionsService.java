package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.dto.questions.QuestionReportRequestDto;
import com.banchan.model.entity.Questions;
import com.banchan.model.entity.User;
import com.banchan.model.entity.Username;
import com.banchan.model.exception.QuestionException;
import com.banchan.model.vo.QuestionCard;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.QuestionsRepository;
import com.banchan.repository.ReportsRepository;
import com.banchan.repository.UserRepository;
import com.banchan.service.reviews.ReviewsService;
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

    @Autowired private ReviewsService reviewsService;
    @Autowired private UserRepository userRepository;

    @Transactional
    public Questions add(QuestionCard questionCard){

        // questionCard 필요 조건 명시

        Questions question = questionsRepository.save(
                Questions.builder()
                        .userId(questionCard.getUserId())
                        .randomOrder(new Random().nextInt(Integer.MAX_VALUE))
                        .writeTime(LocalDateTime.now())
                        .type(questionCard.getType())
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

    public List<QuestionCard> findVotedQuestionCard(long userId, int page, int count){
        return this.findQuestionCardByQuestions(
                questionsRepository.findVotedQuestions(userId, PageRequest.of(page, count))
                        .getContent());
    }

    public List<QuestionCard> findUserMadeQuestionCard(long userId, int page, int count){
        return this.findQuestionCardByQuestions(
                questionsRepository.findAllByUserIdAndReportStateOrderByDecisionAscIdDesc(userId, 0,  PageRequest.of(page, count))
                        .getContent());
    }

    public List<QuestionCard> findNotVotedQuestionCard(long userId, int lastOrder, int count){

        List<QuestionCard> result = findQuestionCardByQuestions(
                questionsRepository.findNotVotedQuestions(userId, lastOrder, count));
        Collections.shuffle(result);

        return result;
    }

    private List<QuestionCard> findQuestionCardByQuestions(List<Questions> questions){
        if(questions == null || questions.size() == 0) throw new QuestionException("QuestionNotFound");

        List<Long> questionIds = questions.stream().map(Questions::getId).collect(Collectors.toList());
        List<Long> userIds = questions.stream().map(Questions::getUserId).collect(Collectors.toList());

        Username testName = new Username(1L, "슬픈", "개발자", null, null);

        User test = new User(1L,
                "TEST",
                20,
                "Y",
                "F",
                null,
                null,
                testName);

        try {

            CompletableFuture<Map<Long, String>> cfUsernameMap = CompletableFuture
                    .supplyAsync(() -> Optional.ofNullable(userRepository.findByIdIn(userIds))
                            .orElse(Arrays.asList(new User[] {test}))
                            .stream()
                            .collect(Collectors.toMap(
                                    User::getId,
                                    user -> Optional.ofNullable(user.getUsername())
                                            .orElse(testName).getPrefix() + " " +
                                            Optional.ofNullable(user.getUsername())
                                                    .orElse(testName).getPostfix()
                            )));

            CompletableFuture<Map<Long, Map<DetailType, String>>> cfDetailMap =
                    questionDetailsService.findQuestionDetails(questionIds);

            CompletableFuture<Map<Long, VoteCount>> cfVoteCountMap =
                    votesService.findVoteCount(questionIds);

            CompletableFuture<Map<Long, Long>> cfReviewCountMap =
                    reviewsService.findReviewCount(questionIds);

            return CompletableFuture.allOf(cfUsernameMap, cfDetailMap, cfVoteCountMap, cfReviewCountMap)
                    .thenApply(ignoredVoid ->
                            this.toQuestionCards(
                                    questions,
                                    cfUsernameMap.join(),
                                    cfDetailMap.join(),
                                    cfVoteCountMap.join(),
                                    cfReviewCountMap.join()))
                    .get();

        } catch (InterruptedException e) { throw new RuntimeException(e);
        } catch (ExecutionException e) { throw new RuntimeException(e); }
    }

    private List<QuestionCard> toQuestionCards(
            List<Questions> questions,
            Map<Long, String> usernameMap,
            Map<Long, Map<DetailType, String>> detailMap,
            Map<Long, VoteCount> voteCountMap,
            Map<Long, Long> reviewCountMap){

        return questions.stream()
                .map(question -> QuestionCard.builder()
                        .id(question.getId())
                        .username(Optional.ofNullable(usernameMap.get(question.getUserId())).orElse("살려줘 제발"))
                        .order(question.getRandomOrder())
                        .userId(question.getUserId())
                        .type(question.getType())
                        .detail(detailMap.get(question.getId()))
                        .vote(voteCountMap.get(question.getId()))
                        .review(Optional.ofNullable(reviewCountMap.get(question.getId())).orElse(0L))
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
