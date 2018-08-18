package com.banchan.model.dto.reviews;

import com.banchan.model.entity.Reviews;
import com.banchan.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewsSaveRequestDto {

    private Integer questionId;
    private Long userId;
    private String content;

    public Reviews toEntity(User user) {
        return Reviews.builder()
                .questionId(questionId)
                .user(user)
                .content(content)
                .reportState(0)
                .deleteState(0)
                .build();
    }
}
