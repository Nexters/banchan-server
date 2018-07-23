package com.banchan.domain;

import com.banchan.domain.question.DetailType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DetailTypeTest {
    @Test
    public void valueOf(){
        for(DetailType detailType : DetailType.values()){

            Assertions.assertEquals(detailType, DetailType.valueOf(detailType.toString()));
            //valueOf(int) check
            Assertions.assertEquals(detailType, DetailType.valueOf(detailType.intValue()));
        }
    }
}
