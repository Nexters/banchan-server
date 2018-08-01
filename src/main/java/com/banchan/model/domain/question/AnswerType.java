package com.banchan.model.domain.question;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public enum AnswerType {

    A(0), B(1);

    private static final Map<Integer, AnswerType> map = Maps.newHashMap();

    @Getter private final Integer value;

    AnswerType(int value) {
        this.value = value;
    }

    static {
        Arrays.stream(AnswerType.values())
                .forEach(type -> map.put(type.value, type));
    }

    public static AnswerType valueOf(Integer value){
        return Optional.ofNullable(map.get(value))
                .orElseThrow(IllegalArgumentException::new);
    }
}
