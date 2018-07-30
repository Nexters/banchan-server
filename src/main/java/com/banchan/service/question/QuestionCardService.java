package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import com.banchan.vo.QuestionCard;
import com.banchan.vo.VoteCount;
import com.banchan.dto.QuestionCardData;
import com.banchan.dto.VoteCountData;
import com.banchan.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionCardService {

    @Autowired private QuestionsService questionsService;
    @Autowired private QuestionsRepository questionsRepository;

    // 페이지네이션 필요
    // 마지막일 경우 LAST_PAGE 같은 걸 넘기고 아니면 MIDDLE_PAGE 같은 걸 넘김
    public List<QuestionCard> questionCards(){
        List<QuestionCardData> datas = questionsRepository.findAllQuestionCardData();

        List<QuestionCard> cards = new ArrayList<>();

        for(QuestionCardData data : datas)
            cards.add(this.makeQuestionCardBy(data));

        return cards;
    }

    public QuestionCard makeQuestionCardBy(QuestionCardData data){

        VoteCount voteCount = this.makeVoteCountBy(data.getVoteCountData());
        Map<DetailType, String> details = questionsService.makeDetailMapBy(data.getQuestion());

        return QuestionCard.builder()
                .id(data.getQuestion().getId())
                .voteCount(voteCount)
                .details(details)
                .build();
    }

    public VoteCount makeVoteCountBy(VoteCountData voteCountData){

        long ansA = voteCountData.getAnsA();
        long ansB = voteCountData.getAnsB();
        long total = voteCountData.getTotal();

        return  VoteCount.builder()
                .ansA(ansA)
                .ansB(ansB)
                .total(total)
                .build();
    }
}
