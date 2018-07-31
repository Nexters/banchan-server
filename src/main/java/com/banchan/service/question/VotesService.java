package com.banchan.service.question;

import com.banchan.domain.question.AnswerType;
import com.banchan.dto.Votes;
import com.banchan.repository.VotesRepository;
import com.banchan.vo.RawVote;
import org.springframework.beans.factory.annotation.Autowired;

public class VotesService {

    @Autowired VotesRepository votesRepository;

    public Votes add(RawVote rawVote){
        Votes vote = Votes.builder()
                .userId(rawVote.getUserId())
                .questionId(rawVote.getQuestionId())
                .answer(AnswerType.valueOf(rawVote.getAnswer()).intValue())
                .build();

        return votesRepository.saveAndFlush(vote);
    }
}
