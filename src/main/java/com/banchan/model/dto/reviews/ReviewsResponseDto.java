package com.banchan.model.dto.reviews;

import com.banchan.model.entity.Reviews;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class ReviewsResponseDto {

    private Integer id;
    private Integer questionId;
    private Long uesrId;
    private String content;
    private Integer reportState;
    private String createdAt;
    private String updatedAt;

    public ReviewsResponseDto(Reviews entity) {
        this.id = entity.getId();
        this.questionId = entity.getQuestionId();
        this.uesrId = entity.getUesrId();
        this.content = entity.getContent();
        this.reportState = entity.getReportState();
        this.createdAt = toStringDateTime(entity.getCreatedAt());
        this.updatedAt = toStringDateTime(entity.getUpdatedAt());
    }

    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
