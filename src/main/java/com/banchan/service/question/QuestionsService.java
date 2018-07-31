package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import com.banchan.domain.question.QuestionType;
import com.banchan.dto.QuestionCardData;
import com.banchan.dto.QuestionDetails;
import com.banchan.dto.Questions;
import com.banchan.repository.QuestionsRepository;
import com.banchan.vo.RawQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class QuestionsService {

    @Autowired QuestionsRepository questionsRepository;

    public Map<DetailType, String> makeDetailMapBy(Questions question){
        Map<DetailType, String> map = new HashMap<>();

        for(QuestionDetails detail : question.getQuestionDetails())
            map.put(
                    DetailType.valueOf(detail.getType()),
                    detail.getContent()
            );

        return map;
    }

    // void 로 할 예정
    public Questions add(RawQuestion rawQuestion){

        Questions question =  Questions.builder()
                .userId(rawQuestion.getUserId())
                .type(QuestionType.valueOf(rawQuestion.getType()).intValue())
                .randomOrder(new Random().nextInt(Integer.MAX_VALUE))
                .writeTime(LocalDateTime.now())
                .build();

        return questionsRepository.saveAndFlush(question);
    }

    public List<QuestionCardData> findAllQuestionCardData(){
        return questionsRepository.findAllQuestionCardData();
    }
}
