package com.banchan.model.entity;

import com.banchan.model.domain.question.DetailTypeAttributeConverter;
import com.banchan.model.domain.question.DetailType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "question_details")
@Builder
public class QuestionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Convert(converter = DetailTypeAttributeConverter.class)
    private DetailType type;

    @Column(name = "content")
    private String content;

    @Column(name = "question_id")
    private Long questionId;
}
