package com.banchan.model.domain.question;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public enum QuestionType {
    A(0), B(1), C(2), D(3);

    private static final Map<Integer, QuestionType> map = Maps.newHashMap();

    @Getter private final Integer value;

    static {
        Arrays.stream(QuestionType.values())
                .forEach(type -> map.put(type.value, type));
    }

    QuestionType(Integer value) {
        this.value = value;
    }

    public static QuestionType valueOf(Integer value){
        return Optional.ofNullable(map.get(value))
                .orElseThrow(IllegalArgumentException::new);
    }
}
