package com.banchan.model.dto;

import com.banchan.model.domain.question.AnswerType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Vote {

    @NotNull
    private Long userId;

    @NotNull
    private Long questionId;

    @NotNull
    private AnswerType answer;

    @NotNull
    private boolean random;
}
