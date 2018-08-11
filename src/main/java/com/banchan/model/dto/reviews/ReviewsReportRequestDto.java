package com.banchan.model.dto.reviews;

import com.banchan.model.entity.Reports;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsReportRequestDto {

    private Integer userId;
    private Integer reviewId;

    public Reports toEntity() {
        return Reports.builder()
                .userId(userId)
                .reviewId(reviewId)
                .build();
    }

}
