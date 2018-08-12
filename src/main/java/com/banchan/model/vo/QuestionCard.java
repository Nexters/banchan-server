package com.banchan.model.vo;

import com.banchan.model.domain.question.DetailType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class QuestionCard {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer order;

    @NotNull
    private Integer userId;

    @Size(min = 3, max = 6) // enum valid 도 찾아보거나 만들어보자
    private Map<DetailType, String> detail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime writeTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private VoteCount vote;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long comment;
}
