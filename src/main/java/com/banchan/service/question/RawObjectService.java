package com.banchan.service.question;

import com.banchan.dto.Questions;
import com.banchan.vo.RawQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class RawObjectService {

    @Autowired QuestionsService questionsService;
    @Autowired QuestionDetailsService questionDetailsService;

    @Transactional
    public Questions add(RawQuestion rawQuestion){
        Map<String, String> details = rawQuestion.getDetails();
        // 이미지 처리 로직 필요. 이미지 업로드 하고 details 에 추가해줘야함

        Questions q = questionsService.add(rawQuestion);
        questionDetailsService.add(q.getId(), details);

        return q;
    }
}
