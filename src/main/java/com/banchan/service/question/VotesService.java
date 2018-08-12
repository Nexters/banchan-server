package com.banchan.service.question;

import com.banchan.model.dto.Vote;
import com.banchan.model.dto.VoteCountData;
import com.banchan.model.entity.Questions;
import com.banchan.model.entity.VotesA;
import com.banchan.model.entity.VotesB;
import com.banchan.model.exception.QuestionException;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.QuestionsRepository;
import com.banchan.repository.VotesARepository;
import com.banchan.repository.VotesBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class VotesService {

    @Autowired VotesARepository votesARepository;
    @Autowired VotesBRepository votesBRepository;

    @Autowired QuestionsRepository questionsRepository;

    private static final long VOTE_COUNT_CONSTRAINT = 5;
    private static final long QUESTION_TIME_CONSTRAINT = 30;

    private static final int VOTE_COUNT_REWARD = 10;
    private static final int QUESTION_TIME_REWARD = 10;

    private static final int RANDOM_REWARD_MIN = 10;
    private static final int RANDOM_REWARD_MAX = 90 - RANDOM_REWARD_MIN;

    public int add(Vote vote){
        CompletableFuture<Integer> reward = saveReward(vote);
        saveVote(vote);

        try {

            return reward.get();

        } catch (InterruptedException e) { throw new RuntimeException(e);
        } catch (ExecutionException e) { throw new RuntimeException(e); }
    }

    private CompletableFuture<Integer> saveReward(Vote vote){

        return CompletableFuture.supplyAsync(
                () -> questionsRepository.findById(vote.getQuestionId())
                        .orElseThrow(() -> new QuestionException("해당 질문이 없습니다.")))
                .thenCombine(votesARepository.countAllByQuestionId(vote.getQuestionId()),
                        (question, voteCount) ->
                                this.rewardByQuestion(question)
                                        + this.rewardByVoteCount(voteCount.longValue())
                                        + (vote.isRandom() ?
                                        new Random().nextInt(RANDOM_REWARD_MAX) + RANDOM_REWARD_MIN : 0));
    }

    private int rewardByQuestion(Questions question){
        int result = 0;

        if(question.getWriteTime()
                .plus(Duration.ofMinutes(this.QUESTION_TIME_CONSTRAINT))
                .isBefore(LocalDateTime.now()))
            result += this.QUESTION_TIME_REWARD;

        return result;
    }

    private int rewardByVoteCount(Long count){
        int result = 0;

        if(count < this.VOTE_COUNT_CONSTRAINT)
            result += this.VOTE_COUNT_REWARD;

        return result;
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

    public CompletableFuture<Map<Integer, VoteCount>> findVoteCount(List<Integer> questionIds){

        return votesARepository.countByQuestionIdInGroupByQuestion(questionIds)
                .thenCombine(votesBRepository.countByQuestionIdInGroupByQuestion(questionIds),
                        (countA, countB) -> {
                            Map<Integer, Long> mapA = countA.stream()
                                    .collect(Collectors.toMap(
                                            VoteCountData::getQuestionId,
                                            VoteCountData::getCount));
                            Map<Integer, Long> mapB = countB.stream()
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
