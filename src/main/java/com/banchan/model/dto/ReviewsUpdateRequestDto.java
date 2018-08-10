package com.banchan.model.dto;

import com.banchan.model.entity.Reviews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewsUpdateRequestDto {

    private Integer reviewId;
    private String content;
}
