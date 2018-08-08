package com.banchan.service.question;

import com.banchan.model.dto.Vote;
import com.banchan.model.dto.VoteCountData;
import com.banchan.model.entity.VotesA;
import com.banchan.model.entity.VotesB;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.VotesARepository;
import com.banchan.repository.VotesBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class VotesService {

    @Autowired VotesARepository votesARepository;
    @Autowired VotesBRepository votesBRepository;

    public Vote add(Vote vote){
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
