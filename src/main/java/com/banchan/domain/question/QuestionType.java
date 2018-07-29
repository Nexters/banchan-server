package com.banchan.domain.question;

public enum  QuestionType {

    NORMAL(1);

    private int value;

    QuestionType(int value) {
        this.value = value;
    }

    public static QuestionType valueOf(int value){
        switch (value){

            default: throw new IllegalStateException(value + "는 QuestionType 으로 변환할 수 없습니다.");
        }
    }
}
