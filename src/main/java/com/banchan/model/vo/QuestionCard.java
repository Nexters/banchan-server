package com.banchan.model.vo;

import com.banchan.model.domain.question.DetailType;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class QuestionCard {

    private Integer id;
    private Integer order;

    private Map<DetailType, String> details;

    private VoteCount voteCount;
    private Long commentCount;
}
