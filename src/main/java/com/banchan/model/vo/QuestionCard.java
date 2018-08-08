package com.banchan.model.vo;

import com.banchan.model.domain.question.DetailType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
@Builder
public class QuestionCard {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull
    private Integer order;

    @NotNull
    private Integer userId;

    @Size(min = 3, max = 6) // enum valid 도 찾아보거나 만들어보자
    private Map<DetailType, String> details;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private VoteCount voteCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long commentCount;
}
