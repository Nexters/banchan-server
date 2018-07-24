package com.banchan.domain.question;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class QuestionCard {

    private int id;
    private Map<DetailType, String> details;
    private VoteCount voteCount;
}
