package com.banchan.domain.question;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class QuestionCard {

    private int id;
    private VoteCount voteCount;
    private Map<DetailType, String> details;
}
