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
    private Integer id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "review_id")
    private Integer reviewId;

    @Builder
    public Reports(Long userId, Integer questionId, Integer reviewId) {
        this.userId = userId;
        this.questionId = questionId;
        this.reviewId = reviewId;
    }
}
