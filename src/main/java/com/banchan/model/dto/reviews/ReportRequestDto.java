package com.banchan.model.dto.reviews;

import com.banchan.model.entity.Reports;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequestDto {

    private Integer userId;
    private Integer reviewId;
    private Integer questionId;

    public Reports toReviewReportEntity() {
        return Reports.builder()
                .userId(userId)
                .reviewId(reviewId)
                .build();
    }

    public Reports toQuestionReportEntity() {
        return Reports.builder()
                .userId(userId)
                .questionId(questionId)
                .build();
    }

}
