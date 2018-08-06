package com.banchan.service.question;

import com.banchan.model.entity.Questions;
import com.banchan.model.vo.QuestionCard;
import com.banchan.repository.QuestionsRepository;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

@Service
public class QuestionsService {

    @Autowired QuestionsRepository questionsRepository;

    @Autowired ImageUploader imageUploader;
    @Autowired QuestionDetailsService questionDetailsService;

    @Transactional
    public Questions add(QuestionCard questionCard){

        // questionCard 필요 조건 명시

        Questions question = questionsRepository.save(
                Questions.builder()
                        .userId(questionCard.getUserId())
                        .randomOrder(new Random().nextInt(Integer.MAX_VALUE))
                        .writeTime(LocalDateTime.now())
                        .build());

        questionDetailsService.add(
                question.getId(),
                EntryStream.of(questionCard.getDetails())
                        .peek(detail -> detail.setValue(
                                detail.getKey().isImgType() ?
                                        imageUploader.upload(
                                                "" + question.getId() + detail.getKey(),
                                                Base64.getDecoder().decode(detail.getValue()))
                                        : detail.getValue()))
                        .toMap());

        return question;
    }

}
