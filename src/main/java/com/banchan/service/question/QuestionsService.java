package com.banchan.service.question;

import com.banchan.DTO.QuestionDetails;
import com.banchan.DTO.Questions;
import com.banchan.DTO.Votes;
import com.banchan.domain.question.AnswerType;
import com.banchan.domain.question.DetailType;
import com.banchan.domain.question.VoteCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsService {

    public Map<DetailType, String> makeDetailMapBy(Questions question){
        Map<DetailType, String> map = new HashMap<>();

        for(QuestionDetails detail : question.getQuestionDetails())
            map.put(
                    DetailType.valueOf(detail.getType()),
                    detail.getContent()
            );

        return map;
    }

    public VoteCount makeVoteCardBy(Questions question){
        List<Votes> votes = question.getVotes();

        int ansA = 0, ansB, total = votes.size();

        for(Votes vote : votes)
            if(AnswerType.valueOf(vote.getAnswer()) == AnswerType.A)
                ansA++;

        ansB = total - ansA;

        return  VoteCount.builder()
                .ansA(ansA)
                .ansB(ansB)
                .total(total)
                .build();
    }
}
