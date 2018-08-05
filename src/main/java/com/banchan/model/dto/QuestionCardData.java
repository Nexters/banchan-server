package com.banchan.model.dto;

import com.banchan.model.entity.QuestionsSingular;
import lombok.Data;

@Data
public class QuestionCardData {

    private QuestionsSingular question;
    private VoteCountData voteCountData;

    public QuestionCardData(QuestionsSingular question, long ansA, long total) {
        this.question = question;
        this.voteCountData = new VoteCountData(ansA, total);
    }
}
