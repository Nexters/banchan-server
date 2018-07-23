package com.banchan.service.question;

import com.banchan.DTO.Questions;
import com.banchan.domain.question.DetailType;
import com.banchan.domain.question.QuestionCard;
import com.banchan.domain.question.VoteCount;
import com.banchan.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class QuestionCardService {

    private QuestionsService questionsService;
    @Autowired private QuestionsRepository questionsRepository;

    public void setQuestionsService(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    public QuestionCard makeQuestionCardBy(Questions question){

        VoteCount voteCount = questionsService.makeVoteCardBy(question);
        Map<DetailType, String> details = questionsService.makeDetailMapBy(question);

        return QuestionCard.builder()
                .id(question.getId())
                .voteCount(voteCount)
                .details(details)
                .build();
    }
}
