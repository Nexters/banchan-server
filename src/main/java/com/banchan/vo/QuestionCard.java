package com.banchan.vo;

import com.banchan.domain.question.DetailType;
import com.banchan.domain.question.QuestionType;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class QuestionCard {

    private int id;
    private QuestionType questionType;
    private Map<DetailType, String> details;
    private VoteCount voteCount;
}
