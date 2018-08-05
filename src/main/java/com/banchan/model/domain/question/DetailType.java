package com.banchan.model.domain.question;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public enum DetailType {
    TXT_Q(1), TXT_A(2), TXT_B(3), IMG_Q(4), IMG_A(5), IMG_B(6);

    private static final Set<DetailType> img = Sets.newHashSet();
    private static final Map<Integer, DetailType> map = Maps.newHashMap();
    @Getter private final Integer value;

    static {
        Arrays.stream(DetailType.values())
                .forEach(type -> map.put(type.value, type));

        img.addAll(Arrays.asList(new DetailType[]{IMG_Q, IMG_A, IMG_B}));
    }

    DetailType(int value) {
        this.value = value;
    }

    public static DetailType valueOf(Integer value){
        return Optional.ofNullable(map.get(value))
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isImgType(){
        return checkImgType(this);
    }

    public static boolean checkImgType(DetailType type){
        return img.contains(type);
    }
}
