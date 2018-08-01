package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.entity.QuestionDetails;
import com.banchan.repository.QuestionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionDetailsService {

    @Autowired QuestionDetailsRepository questionDetailsRepository;

    private QuestionDetails makeQuestionDetails(int questionId, String type, String content){
        return QuestionDetails.builder()
                .questionId(questionId)
                .type(DetailType.valueOf(type).intValue())
                .content(content)
                .build();
    }

    public List<QuestionDetails> add(int questionId, Map<String, String> details){

        final List<QuestionDetails> questionDetails = new ArrayList<>();

        details.keySet().stream()
                .map(type -> this.makeQuestionDetails(questionId, type, details.get(type)))
                .forEach(questionDetails::add);

        return questionDetailsRepository.saveAll(questionDetails);
    }

}
