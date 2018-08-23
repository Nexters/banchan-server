package com.banchan.model.dto.reviews;

import com.banchan.model.entity.Reviews;
import com.banchan.model.entity.User;
import com.banchan.model.entity.Username;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class ReviewsResponseDto {

    private Long id;
    private Long questionId;
    private String userName;
    private String content;
    private Integer reportState;
    private String createdAt;
    private String updatedAt;
    private Long userId;
    private Integer answer; //0: 작성자, 1: A or O 답변, 2: B or X 답변

    public ReviewsResponseDto(Reviews entity, Integer answer) {
        this.id = entity.getId();
        this.questionId = entity.getQuestionId();
        System.out.println(entity.getUser());
        this.userName = entity.getUser().getUsername().getPrefix() + " " + entity.getUser().getUsername().getPostfix();
        this.content = entity.getContent();
        this.reportState = entity.getReportState();
        this.createdAt = toStringDateTime(entity.getCreatedAt());
        this.updatedAt = toStringDateTime(entity.getUpdatedAt());
        System.out.println(answer);
        this.userId = entity.getUser().getId();
        this.answer = answer;
    }

    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
