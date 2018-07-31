package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import com.banchan.domain.question.QuestionType;
import com.banchan.dto.QuestionCardData;
import com.banchan.dto.Questions;
import com.banchan.dto.VoteCountData;
import com.banchan.vo.QuestionCard;
import com.banchan.vo.VoteCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionCardService {

    @Autowired private QuestionsService questionsService;

    // 페이지네이션 필요
    // 마지막일 경우 LAST_PAGE 같은 걸 넘기고 아니면 MIDDLE_PAGE 같은 걸 넘김
    public List<QuestionCard> questionCards(){
        List<QuestionCardData> dataList = questionsService.findAllQuestionCardData();

        List<QuestionCard> cards = new ArrayList<>();

        for(QuestionCardData data : dataList)
            cards.add(this.makeQuestionCardBy(data));

        return cards;
    }

    public QuestionCard makeQuestionCardBy(QuestionCardData data){

        Questions q = data.getQuestion();
        VoteCount voteCount = this.makeVoteCountBy(data.getVoteCountData());
        Map<DetailType, String> details = questionsService.makeDetailMapBy(data.getQuestion());

        return QuestionCard.builder()
                .id(q.getId())
                .questionType(QuestionType.valueOf(q.getType()))
                .order(q.getRandomOrder())
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
