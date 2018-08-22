package com.banchan.model.dto.reviews;

import com.banchan.model.entity.Reviews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewsUpdateRequestDto {

    private Long reviewId;
    private String content;
}
