package com.banchan.util;

import java.util.function.Function;

public class Wrapper {

    public static <T, R, E extends Exception> Function<T, R> wrap(FunctionWithException<T, R, E> f){

        return arg -> {
            try{
                return f.apply(arg);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        };
    }
}
