package com.banchan.model.entity;

import com.banchan.model.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class Reports extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "review_id")
    private Long reviewId;

    @Builder
    public Reports(Long userId, Long questionId, Long reviewId) {
        this.userId = userId;
        this.questionId = questionId;
        this.reviewId = reviewId;
    }
}
