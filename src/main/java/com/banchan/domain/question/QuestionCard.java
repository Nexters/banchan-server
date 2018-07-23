package com.banchan.domain.question;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class QuestionCard {

    private int id;

    @Singular("addDetail")
    private Map<DetailType, String> details;
}
