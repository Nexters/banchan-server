package com.banchan.service.question;

import com.banchan.model.domain.question.RewardType;
import com.banchan.model.exception.QuestionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class Rewarder {

    @Value("${reward.constraint.speaker}")
    private int constraintFirst;

    @Value("${reward.constraint.new}")
    private int constraintNew;

    @Value("${reward.constraint.random}")
    private int constraintRandom;

    @Value("${reward.value.speaker}")
    private int valueBasic;

    @Value("${reward.value.speaker}")
    private int valueFirst;

    @Value("${reward.value.new}")
    private int valueNew;

    @Value("${reward.value.random.min}")
    private int valueRandomMin;

    @Value("${reward.value.random.max}")
    private int valueRandomMax;

    @Value("${reward.value.decision}")
    private int valueDecision;

    private final double DIVISION = 10.0;

    public boolean checkNew(LocalDateTime time){
        return !time.plus(Duration.ofMinutes(this.constraintNew))
                .isBefore(LocalDateTime.now());
    }

    public boolean checkFirst(Long count){
        return count < this.constraintFirst;
    }

    public boolean checkRandom(){
        return new Random().nextInt(100) < this.constraintRandom;
    }

    public Double rewardOf(RewardType type){
        switch (type){
            case BASIC: return this.valueBasic / this.DIVISION;
            case NEW: return this.valueNew / this.DIVISION;
            case FIRST: return this.valueFirst / this.DIVISION;
            case RANDOM:
                return (new Random()
                        .nextInt(this.valueRandomMax - this.valueRandomMin + 1) + this.valueRandomMin) / this.DIVISION;
            case SAME: return this.valueDecision / this.DIVISION;
            default: throw new QuestionException("올바른 리워드 타입이 아닙니다.");
        }
    }
}
