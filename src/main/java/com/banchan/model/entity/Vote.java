package com.banchan.model.entity;

import com.banchan.model.domain.question.AnswerType;
import com.banchan.model.domain.question.AnswerTypeAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "answer")
    @Convert(converter = AnswerTypeAttributeConverter.class)
    private AnswerType answer;

    @JsonIgnore
    @Column(name = "vote_time")
    private LocalDateTime voteTime;

    @Transient
    private boolean random;
}
