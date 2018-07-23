package com.banchan.config.question;

import com.banchan.service.question.QuestionCardService;
import com.banchan.service.question.QuestionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public QuestionCardService questionCardService(){
        QuestionCardService questionCardService = new QuestionCardService();
        questionCardService.setQuestionsService(questionsService());

        return questionCardService;
    }

    @Bean
    public QuestionsService questionsService(){
        return new QuestionsService();
    }
}
