package com.banchan.service.question;

import com.banchan.model.domain.question.AnswerType;
import com.banchan.model.domain.question.RewardType;
import com.banchan.model.entity.Questions;
import com.banchan.model.entity.Reviews;
import com.banchan.model.entity.RewardHistory;
import com.banchan.model.entity.Vote;
import com.banchan.model.exception.QuestionException;
import com.banchan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VoteService {
    @Autowired VoteRepository voteRepository;

    @Autowired QuestionsRepository questionsRepository;
    @Autowired RewardHistoryRepository rewardHistoryRepository;

    @Autowired Rewarder rewarder;

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
        return Stream.of(voteRepository.findAllByQuestionId(vote.getQuestionId()).stream()
                .map(Vote::getUserId)
                .map(userId -> this.makeRewardHistory(userId, RewardType.SAME, rewarder.rewardOf(RewardType.SAME)))
                .collect(Collectors.toList()))
                .peek(rewardHistoryRepository::saveAll)
                .map(List::size)
                .findAny().orElse(0);
    }

    private Double reward(Vote vote, Questions question) {
        return Stream.of(Arrays.stream(new RewardHistory[] {
                this.rewardInBasic(vote),
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

    private RewardHistory rewardInBasic(Vote vote){
        return this.makeRewardHistory(vote.getUserId(), RewardType.BASIC, rewarder.rewardOf(RewardType.BASIC));
    }

    private RewardHistory rewardInNew(Vote vote, Questions question){
        return rewarder.checkNew(question.getWriteTime()) ?
                this.makeRewardHistory(vote.getUserId(), RewardType.NEW, rewarder.rewardOf(RewardType.NEW)) : null;
    }

    private RewardHistory rewardInFirst(Vote vote, Questions question){
        return rewarder.checkFirst(voteRepository.countAllByQuestionId(question.getId())) ?
                this.makeRewardHistory(vote.getUserId(), RewardType.FIRST, rewarder.rewardOf(RewardType.FIRST)) : null;
    }

    private RewardHistory rewardInRandom(Vote vote){
        return vote.isRandom() ?
                this.makeRewardHistory(vote.getUserId(), RewardType.RANDOM, rewarder.rewardOf(RewardType.RANDOM)) : null;
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
        return voteRepository.save(vote);
    }

    public Integer getAnswer(Long questionId, Reviews reviews) {
        if(questionsRepository.findById(questionId).get().getUserId() == reviews.getUser().getId())
            return 0;

        return voteRepository.findByQuestionIdAndUserId(questionId, reviews.getUser().getId())
                .getAnswer() == AnswerType.A ? 1 : 2;
    }
}
