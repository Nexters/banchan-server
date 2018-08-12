package com.banchan.model.dto;

import com.banchan.model.domain.question.AnswerType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Vote {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer questionId;

    @NotNull
    private AnswerType answer;

    @NotNull
    private boolean random;
}
