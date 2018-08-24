package optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args){
        new OptionalTest().filterTest();
    }

    public void filterTest(){
        System.out.println(Optional.of(10).filter(n -> n < 10).orElse(0));
    }
}
