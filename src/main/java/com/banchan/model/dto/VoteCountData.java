package com.banchan.model.dto;

import lombok.Data;

@Data
public class VoteCountData {

    private Integer questionId;
    private Long count;

    public VoteCountData(Integer questionId, Long count) {
        this.questionId = questionId;
        this.count = count;
    }
}
