package com.banchan.model.domain.question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnswerTypeTest {

    @Test
    public void valueOf(){
        for(AnswerType answerType : AnswerType.values()){

            Assertions.assertEquals(answerType, AnswerType.valueOf(answerType.toString()));
            //valueOf(int) check
            Assertions.assertEquals(answerType, AnswerType.valueOf(answerType.getValue()));
        }
    }
}
