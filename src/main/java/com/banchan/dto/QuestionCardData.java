package com.banchan.dto;

import lombok.Data;

@Data
public class QuestionCardData {

    private Questions question;
    private VoteCountData voteCountData;

    public QuestionCardData(Questions question, long ansA, long total) {
        this.question = question;
        this.voteCountData = new VoteCountData(ansA, total);
    }
}
