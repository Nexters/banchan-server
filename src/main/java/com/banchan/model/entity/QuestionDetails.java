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
    private DetailType type;

    @Column(name = "content")
    @Convert(converter = DeatailTypeAttributeConverter.class)
    private String content;

    @Column(name = "question_id")
    private Integer questionId;
}
