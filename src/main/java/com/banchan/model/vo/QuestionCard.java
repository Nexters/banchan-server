package com.banchan.model.vo;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.domain.question.QuestionType;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class QuestionCard {

    private int id;
    private QuestionType questionType;
    private int order;
    private Map<DetailType, String> details;
    private VoteCount voteCount;
}
