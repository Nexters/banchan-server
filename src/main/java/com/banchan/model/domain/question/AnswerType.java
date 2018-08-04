package com.banchan.model.domain.question;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public enum AnswerType {

    A(false), B(true);

    private static final Map<Boolean, AnswerType> map = Maps.newHashMap();

    @Getter private final Boolean value;

    AnswerType(Boolean value) {
        this.value = value;
    }

    static {
        Arrays.stream(AnswerType.values())
                .forEach(type -> map.put(type.value, type));
    }

    public static AnswerType valueOf(Boolean value){
        return Optional.ofNullable(map.get(value))
                .orElseThrow(IllegalArgumentException::new);
    }
}
