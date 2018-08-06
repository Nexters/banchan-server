package com.banchan.service.question;

import com.banchan.model.entity.Questions;
import com.banchan.model.entity.Votes;
import com.banchan.model.vo.VoteCount;
import com.banchan.repository.VotesARepository;
import com.banchan.repository.VotesBRepository;
import com.banchan.repository.VotesRepository;
import com.google.common.collect.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class VotesService {

    @Autowired VotesARepository votesARepository;
    @Autowired VotesBRepository votesBRepository;

    @Autowired VotesRepository votesRepository;

    public Votes add(Votes vote){
        return votesRepository.save(vote);
    }

    public List<VoteCount> findVoteCount(List<Questions> questionsList){

        try {

            return votesARepository.countByQuestionInGroupByQuestion(questionsList)
                    .thenCombine(votesBRepository.countByQuestionInGroupByQuestion(questionsList),
                            (countA, countB) ->
                                    Streams.zip(countA.stream(), countB.stream(), VoteCount::new)
                                            .collect(Collectors.toList())).get();

        } catch (InterruptedException e) { throw new RuntimeException(e);
        } catch (ExecutionException e) { throw new RuntimeException(e); }
    }
}
