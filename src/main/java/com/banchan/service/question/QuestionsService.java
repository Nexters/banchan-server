package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.entity.QuestionDetails;
import com.banchan.model.entity.QuestionsSingular;
import com.banchan.model.response.UploadResponse;
import com.banchan.model.vo.QuestionCard;
import com.banchan.repository.QuestionsRepository;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestionsService {

    @Autowired QuestionsRepository questionsRepository;

    @Autowired ImageUploader imageUploader;

    @Transactional
    public QuestionsSingular add(QuestionCard questionCard){

        question.setRandomOrder(new Random().nextInt(Integer.MAX_VALUE));
        question.setWriteTime(LocalDateTime.now());

        Integer questionId = questionsRepository.save(question).getId();

        Map<Integer, UploadResponse> responses = question.getQuestionDetails().stream()
                .filter(detail -> DetailType.checkImgType(detail.getType()))
                .peek(detail ->
                    detail.setContent(this.key(detail, questionId)))
                .collect(Collectors.toMap(
                        detail -> detail.getType(),
                        detail -> imageUploader.upload(this.key(detail, questionId), Base64.getDecoder().decode(detail.getContent()))
                ));

        responses.


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

    public String key(QuestionDetails detail, int questionId){
        return new StringBuilder(questionId).append(DetailType.valueOf(detail.getType()).name()).toString();
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
