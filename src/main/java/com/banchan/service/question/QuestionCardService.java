package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import com.banchan.domain.question.QuestionCard;
import com.banchan.domain.question.VoteCount;
import com.banchan.dto.RawQuestionCard;
import com.banchan.dto.VoteCountRaw;
import com.banchan.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionCardService {

    private QuestionsService questionsService;
    @Autowired private QuestionsRepository questionsRepository;

    public void setQuestionsService(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    public List<QuestionCard> questionCards(){
        List<RawQuestionCard> raws = questionsRepository.findAllRawQuestionCard();

        List<QuestionCard> cards = new ArrayList<>();

        for(RawQuestionCard raw : raws)
            cards.add(this.makeQuestionCardBy(raw));

        return cards;
    }

    public QuestionCard makeQuestionCardBy(RawQuestionCard raw){

        VoteCount voteCount = this.makeVoteCountBy(raw.getVoteCountRaw());
        Map<DetailType, String> details = questionsService.makeDetailMapBy(raw.getQuestion());

        return QuestionCard.builder()
                .id(raw.getQuestion().getId())
                .voteCount(voteCount)
                .details(details)
                .build();
    }

    public VoteCount makeVoteCountBy(VoteCountRaw voteCountRaw){

        long ansA = voteCountRaw.getAnsA();
        long ansB = voteCountRaw.getAnsB();
        long total = voteCountRaw.getTotal();

        return  VoteCount.builder()
                .ansA(ansA)
                .ansB(ansB)
                .total(total)
                .build();
    }
}
