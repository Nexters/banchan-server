package com.banchan.model.dto.questions;

import com.banchan.model.entity.Reports;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionReportRequestDto {

    private Long userId;
    private Long questionId;

    public Reports toQuestionReportEntity() {
        return Reports.builder()
                .userId(userId)
                .questionId(questionId)
                .build();
    }
}
