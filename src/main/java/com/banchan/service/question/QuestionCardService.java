package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.dto.QuestionCardData;
import com.banchan.model.entity.QuestionsSingular;
import com.banchan.model.vo.QuestionCard;
import com.banchan.model.vo.VoteCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionCardService {
    @Autowired
    private QuestionsService questionsService;

    // 페이지네이션 필요
    // 마지막일 경우 LAST_PAGE 같은 걸 넘기고 아니면 MIDDLE_PAGE 같은 걸 넘김
    public List<QuestionCard> questionCards(){

        return questionsService.findAllQuestionCardData().stream()
                .map(this::makeQuestionCardBy)
                .collect(Collectors.toList());
    }

    public QuestionCard makeQuestionCardBy(QuestionCardData data){

        QuestionsSingular q = data.getQuestion();
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
