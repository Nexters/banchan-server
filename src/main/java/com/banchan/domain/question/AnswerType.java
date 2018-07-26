package com.banchan.domain.question;

import com.banchan.repository.QuestionsRepository;

public enum AnswerType {

    A(QuestionsRepository.ANS_A), B(QuestionsRepository.ANS_B);

    private final int value;

    AnswerType(int value) {
        this.value = value;
    }

    public int intValue(){
        return this.value;
    }

    public static AnswerType valueOf(int value){
        switch(value){
            case QuestionsRepository.ANS_A : return A;
            case QuestionsRepository.ANS_B : return B;
            default: throw new IllegalStateException(value + "는 AnswerType 으로 변환할 수 없습니다.");
        }
    }
}
