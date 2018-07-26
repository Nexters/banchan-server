package com.banchan.dto;

import lombok.Data;

@Data
public class RawQuestionCard {

    private Questions question;
    private VoteCountRaw voteCountRaw;

    public RawQuestionCard(Questions question, long ansA, long total) {
        this.question = question;
        this.voteCountRaw = new VoteCountRaw(ansA, total);
    }
}
