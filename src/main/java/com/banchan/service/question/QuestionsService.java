package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import com.banchan.dto.QuestionDetails;
import com.banchan.dto.Questions;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
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

}
