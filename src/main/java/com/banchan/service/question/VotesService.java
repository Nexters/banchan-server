package com.banchan.service.question;

import com.banchan.model.domain.question.AnswerType;
import com.banchan.model.domain.question.RewardType;
import com.banchan.model.dto.Vote;
import com.banchan.model.dto.VoteCountData;
import com.banchan.model.entity.*;
import com.banchan.model.exception.QuestionException;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.QuestionsRepository;
import com.banchan.repository.RewardHistoryRepository;
import com.banchan.repository.VotesARepository;
import com.banchan.repository.VotesBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VotesService {

    @Autowired VotesARepository votesARepository;
    @Autowired VotesBRepository votesBRepository;

    @Autowired QuestionsRepository questionsRepository;
    @Autowired RewardHistoryRepository rewardHistoryRepository;

    // properties 에 추가 예정
    @Value("${reward.constraint.speaker}")
    private int constraintFirst;

    @Value("${reward.constraint.new}")
    private int constraintNew;

    @Value("${reward.value.speaker}")
    private int valueFirst;

    @Value("${reward.value.new}")
    private int valueNew;

    @Value("${reward.value.random.min}")
    private int valueRandomMin;

    @Value("${reward.value.random.max}")
    private int valueRandomMax;

    @Value("${reward.value.decision}")
    private int valueDecision;

    @Transactional
    public double add(Vote vote){
        Questions question = questionsRepository.findById(vote.getQuestionId())
                .orElseThrow(() -> new QuestionException("해당 질문이 없습니다."));

        if(question.getUserId().equals(vote.getUserId())) {
            question.setDecision(vote.getAnswer());
            questionsRepository.save(question);
            return distributeReward(vote);
        }

        saveVote(vote);
        return reward(vote, question);
    }

    private int distributeReward(Vote vote){
        Stream<Long> stream = null;

        if(AnswerType.A.equals(vote.getAnswer()))
            stream = votesARepository.findAllByQuestionId(vote.getQuestionId()).stream()
                    .map(VotesA::getUserId);
        else
            stream = votesBRepository.findAllByQuestionId(vote.getQuestionId()).stream()
                    .map(VotesB::getUserId);

        return Stream.of(stream
                .map(userId -> this.makeRewardHistory(userId, RewardType.SAME, this.valueDecision / 100.0))
                .collect(Collectors.toList()))
                .peek(rewardHistoryRepository::saveAll)
                .map(List::size)
                .findAny().orElse(0);
    }

    private Double reward(Vote vote, Questions question) {
        return Stream.of(Arrays.stream(new RewardHistory[] {
                this.rewardInNew(vote, question),
                this.rewardInFirst(vote, question),
                this.rewardInRandom(vote)})
                .filter(rewardHistory -> rewardHistory != null)
                .collect(Collectors.toList()))
                .peek(rewardHistoryRepository::saveAll)
                .flatMap(rewardHistories -> rewardHistories.stream())
                .map(RewardHistory::getReward)
                .reduce((r1, r2) -> r1 + r2).orElse(0.0);
    }

    private RewardHistory rewardInNew(Vote vote, Questions question){
        return question.getWriteTime()
                .plus(Duration.ofMinutes(this.constraintNew))
                .isBefore(LocalDateTime.now()) ?
                this.makeRewardHistory(vote.getUserId(), RewardType.NEW, this.valueNew / 100.0) :
                null;
    }

    private RewardHistory rewardInFirst(Vote vote, Questions question){
        return votesARepository.countAllByQuestionId(question.getId()).longValue() < this.constraintFirst ?
                this.makeRewardHistory(vote.getUserId(), RewardType.FIRST, this.valueFirst / 100.0) :
                null;
    }

    private RewardHistory rewardInRandom(Vote vote){
        return vote.isRandom() ?
                this.makeRewardHistory(vote.getUserId(), RewardType.RANDOM,
                        (new Random().nextInt(this.valueRandomMax - this.valueRandomMin + 1) + this.valueRandomMin) / 100.0) :
                null;
    }

    private RewardHistory makeRewardHistory(Long userId, RewardType type, Double reward){
        return RewardHistory.builder()
                .userId(userId)
                .type(type)
                .reward(reward)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private Vote saveVote(Vote vote){
        switch(vote.getAnswer()){
            case A:
                votesARepository.save(VotesA.builder()
                        .questionId(vote.getQuestionId())
                        .userId(vote.getUserId())
                        .voteTime(LocalDateTime.now())
                        .build());
                break;
            case B:
                votesBRepository.save(VotesB.builder()
                        .questionId(vote.getQuestionId())
                        .userId(vote.getUserId())
                        .voteTime(LocalDateTime.now())
                        .build());
                break;
            default:
                throw new IllegalArgumentException("적합하지 않은 AnswerType 입니다.");
        }

        return vote;
    }

    public CompletableFuture<Map<Long, VoteCount>> findVoteCount(List<Long> questionIds){

        return votesARepository.countByQuestionIdInGroupByQuestion(questionIds)
                .thenCombine(votesBRepository.countByQuestionIdInGroupByQuestion(questionIds),
                        (countA, countB) -> {
                            Map<Long, Long> mapA = countA.stream()
                                    .collect(Collectors.toMap(
                                            VoteCountData::getQuestionId,
                                            VoteCountData::getCount));
                            Map<Long, Long> mapB = countB.stream()
                                    .collect(Collectors.toMap(
                                            VoteCountData::getQuestionId,
                                            VoteCountData::getCount));

                            return questionIds.stream()
                                    .collect(Collectors.toMap(
                                            questionId -> questionId,
                                            questionId -> new VoteCount(
                                                    Optional.ofNullable(mapA.get(questionId)).orElse((long) 0),
                                                    Optional.ofNullable(mapB.get(questionId)).orElse((long) 0))));
                        });
    }

    public Integer getAnswer(Long questionId, Reviews reviews) {
        if ( questionsRepository.findById(questionId).get().getUserId() == reviews.getUser().getId()) {
            return 0;
        }
        if ( votesARepository.countByQuestionIdAndUserId(questionId, reviews.getUser().getId()) >= 1) {
            return 1;
        }
        return 2;
    }
}
