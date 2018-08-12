package com.banchan.model.vo;

import com.banchan.model.domain.question.DetailType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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


    // Swagger 를 위한 getter
    @ApiModelProperty(hidden = true)
    public Integer getId() {
        return id;
    }

    @ApiModelProperty(hidden = true)
    public Integer getOrder() {
        return order;
    }

    @ApiModelProperty(hidden = true)
    public LocalDateTime getWriteTime() {
        return writeTime;
    }

    @ApiModelProperty(hidden = true)
    public VoteCount getVote() {
        return vote;
    }

    @ApiModelProperty(hidden = true)
    public Long getComment() {
        return comment;
    }
}
