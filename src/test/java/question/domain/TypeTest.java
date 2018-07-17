package question.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeTest {
    @Test
    public void valueOf(){
        for(Type type : Type.values()){
            Assertions.assertEquals(type, Type.valueOf(type.toString()));
        }
    }
}
