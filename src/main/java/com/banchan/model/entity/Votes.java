package com.banchan.model.entity;

import com.banchan.model.domain.question.AnswerType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Builder
public class Votes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Column(name = "answer", nullable = false)
    private AnswerType answer;
}
