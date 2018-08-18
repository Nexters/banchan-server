package com.banchan.model.dto;

import lombok.Data;

@Data
public class ReviewCountData {

    private Long questionId;
    private Long reviewCount;

    public ReviewCountData(Long questionId, Long reviewCount) {
        this.questionId = questionId.longValue();
        this.reviewCount = reviewCount;
    }
}
