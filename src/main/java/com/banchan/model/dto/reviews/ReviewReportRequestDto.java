package com.banchan.model.dto.reviews;

import com.banchan.model.entity.Reports;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReportRequestDto {

    private Long userId;
    private Long reviewId;

    public Reports toReviewReportEntity() {
        return Reports.builder()
                .userId(userId)
                .reviewId(reviewId)
                .build();
    }

    @Builder
    public ReviewReportRequestDto(Long userId, Long reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;
    }

}
