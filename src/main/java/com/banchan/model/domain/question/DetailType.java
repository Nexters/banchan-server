package com.banchan.model.domain.question;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public enum DetailType {
    QUESTION(1), ANSWER_A(2), ANSWER_B(3), IMG_A(4), IMG_B(5), IMG_Q(6);

    private static final Set<Integer> img = Sets.newHashSet();
    private static final Map<Integer, DetailType> map = Maps.newHashMap();
    @Getter private final Integer value;

    static {
        Arrays.stream(DetailType.values())
                .forEach(type -> map.put(type.value, type));
    }

    DetailType(int value) {
        this.value = value;
    }

    public static DetailType valueOf(Integer value){
        return Optional.ofNullable(map.get(value))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static boolean checkImgType(Integer value){
        return img.contains(value);
    }
}
