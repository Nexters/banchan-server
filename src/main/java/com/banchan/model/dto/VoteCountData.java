package com.banchan.model.dto;

import lombok.Data;

@Data
public class VoteCountData {

    private Long questionId;
    private Long count;

    public VoteCountData(Long questionId, Long count) {
        this.questionId = questionId;
        this.count = count;
    }
}
