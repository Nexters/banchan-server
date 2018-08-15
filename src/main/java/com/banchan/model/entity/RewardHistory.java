package com.banchan.model.entity;

import com.banchan.model.domain.question.AnswerType;
import com.banchan.model.domain.question.RewardType;
import com.banchan.model.domain.question.RewardTypeAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "reward_histories")
public class RewardHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type")
    @Convert(converter = RewardTypeAttributeConverter.class)
    private RewardType type;

    @Column(name = "reward")
    private Double reward;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
