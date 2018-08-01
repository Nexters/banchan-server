package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.entity.QuestionDetails;
import com.banchan.model.entity.Questions;
import com.banchan.model.entity.QuestionsSingular;
import com.banchan.model.vo.RawQuestion;
import com.banchan.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QuestionsService {

    @Autowired
    QuestionsRepository questionsRepository;

    public QuestionsSingular add(RawQuestion rawQuestion){

    }

    public Map<DetailType, String> makeDetailMapBy(QuestionsSingular question){
        Map<DetailType, String> map = new HashMap<>();

        for(QuestionDetails detail : question.getQuestionDetails())
            map.put(
                    DetailType.valueOf(detail.getType()),
                    detail.getContent()
            );

        return map;
    }
    @Transactional
    public Questions add(RawQuestion rawQuestion){
        Map<String, String> details = rawQuestion.getDetails();
        Questions q = questionsService.add(rawQuestion);

        // 이미지 처리 로직 필요. 이미지 업로드 하고 details 에 추가해줘야함

        questionDetailsService.add(q.getId(), details);

        return q;
    }

    // void 로 할 예정
    public QuestionsSingular add(RawQuestion rawQuestion){

        QuestionsSingular question =  QuestionsSingular.builder()
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
