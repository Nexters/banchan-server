package com.banchan.domain.question;

public enum AnswerType {

    A(0), B(1);

    private int value;

    AnswerType(int value) {
        this.value = value;
    }

    public int intValue(){
        return this.value;
    }

    public static AnswerType valueOf(int value){
        switch(value){
            case 0 : return A;
            case 1 : return B;
            default: throw new IllegalStateException(value + "는 AnswerType 으로 변환할 수 없습니다.");
        }
    }
}
