package com.banchan.model.domain.question;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public enum RewardType {

    NEW("N"), FIRST("F"), RANDOM("R");

    private static final Map<String, RewardType> map = Maps.newHashMap();

    @Getter
    private final String value;

    RewardType(String value) {
        this.value = value;
    }

    static {
        Arrays.stream(RewardType.values())
                .forEach(type -> map.put(type.value, type));
    }

    public static RewardType valueOfString(String value){
        return Optional.ofNullable(map.get(value))
                .orElse(null);
    }
}
