package com.banchan.domain.question;

public enum  QuestionType {

    DEFAULT_QUESTION(1), INCLUDE_QUESTION_IMAGE(2); // 조금 상의해봐야 할 것 같음

    private int value;

    QuestionType(int value) {
        this.value = value;
    }

    public static QuestionType valueOf(int value){
        switch (value){
            case 1: return DEFAULT_QUESTION;
            default: throw new IllegalStateException(value + "는 QuestionType 으로 변환할 수 없습니다.");
        }
    }

    public int intValue(){
        return this.value;
    }
}
