package com.banchan.service.question;

import com.banchan.model.entity.QuestionsSingular;
import com.banchan.model.entity.Votes;
import com.banchan.model.vo.RawQuestion;
import com.banchan.model.vo.RawVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class RawObjectService {

    @Autowired QuestionsService questionsService;
    @Autowired VotesService votesService;
    @Autowired QuestionDetailsService questionDetailsService;



    public Votes add(RawVote rawVote){
        return votesService.add(rawVote);
    }
}
