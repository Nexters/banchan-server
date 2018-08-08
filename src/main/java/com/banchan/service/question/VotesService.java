package com.banchan.service.question;

import com.banchan.model.dto.VoteCountData;
import com.banchan.model.entity.Votes;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.VotesARepository;
import com.banchan.repository.VotesBRepository;
import com.banchan.repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class VotesService {

    @Autowired VotesARepository votesARepository;
    @Autowired VotesBRepository votesBRepository;

    @Autowired VotesRepository votesRepository;

    public Votes add(Votes vote){
        return votesRepository.save(vote);
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
