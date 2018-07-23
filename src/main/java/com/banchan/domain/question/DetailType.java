package com.banchan.domain.question;

public enum DetailType {
    QUESTION(1), ANSWER_A(2), ANSWER_B(3), IMG_A(4), IMG_B(5), IMG_Q(6);

    private final int value;

    DetailType(int value) {
        this.value = value;
    }

    public int intValue(){
        return this.value;
    }

    public static DetailType valueOf(int value){
        switch(value){
            case 1: return QUESTION;
            case 2: return ANSWER_A;
            case 3: return ANSWER_B;
            case 4: return IMG_A;
            case 5: return IMG_B;
            case 6: return IMG_Q;
            default: throw new IllegalStateException(value + "는 DetailType 으로 변환할 수 없습니다.");
        }
    }
}
