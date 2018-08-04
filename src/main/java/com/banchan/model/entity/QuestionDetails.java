package com.banchan.model.entity;

import com.banchan.model.domain.question.DeatailTypeAttributeConverter;
import com.banchan.model.domain.question.DetailType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Builder
public class QuestionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    @Convert(converter = DeatailTypeAttributeConverter.class)
    private DetailType type;

    @Column(name = "content")
    private String content;

    @Column(name = "question_id")
    private Integer questionId;
}
