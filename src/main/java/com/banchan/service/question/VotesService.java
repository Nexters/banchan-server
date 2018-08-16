package com.banchan.service.question;

import com.banchan.model.domain.question.AnswerType;
import com.banchan.model.domain.question.RewardType;
import com.banchan.model.dto.Vote;
import com.banchan.model.dto.VoteCountData;
import com.banchan.model.entity.Questions;
import com.banchan.model.entity.RewardHistory;
import com.banchan.model.entity.VotesA;
import com.banchan.model.entity.VotesB;
import com.banchan.model.exception.QuestionException;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.QuestionsRepository;
import com.banchan.repository.RewardHistoryRepository;
import com.banchan.repository.VotesARepository;
import com.banchan.repository.VotesBRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final long VOTE_COUNT_CONSTRAINT = 5;
    private static final long QUESTION_TIME_CONSTRAINT = 30;

    private static final double VOTE_COUNT_REWARD = 10; // %
    private static final double QUESTION_TIME_REWARD = 10; // %

    private static final int RANDOM_REWARD_MIN = 10; // %
    private static final int RANDOM_REWARD_MAX = 90 - RANDOM_REWARD_MIN; // %

    private static final double SAME_REWARD = 10; //%

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
                .map(userId -> this.makeRewardHistory(userId, RewardType.SAME, this.SAME_REWARD / 100))
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
                .plus(Duration.ofMinutes(this.QUESTION_TIME_CONSTRAINT))
                .isBefore(LocalDateTime.now()) ?
                this.makeRewardHistory(vote.getUserId(), RewardType.NEW, this.QUESTION_TIME_REWARD / 100) :
                null;
    }

    private RewardHistory rewardInFirst(Vote vote, Questions question){
        return votesARepository.countAllByQuestionId(question.getId()).longValue() < this.VOTE_COUNT_CONSTRAINT ?
                this.makeRewardHistory(vote.getUserId(), RewardType.FIRST, this.VOTE_COUNT_REWARD / 100) :
                null;
    }

    private RewardHistory rewardInRandom(Vote vote){
        return vote.isRandom() ?
                this.makeRewardHistory(vote.getUserId(), RewardType.RANDOM,
                        ((double) new Random().nextInt(this.RANDOM_REWARD_MAX) + this.RANDOM_REWARD_MIN) / 100) :
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
}
