package com.banchan.model.entity;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.domain.question.DetailTypeAttributeConverter;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "question_details")
@Builder
public class QuestionDetailsSingular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    @Convert(converter = DetailTypeAttributeConverter.class)
    private DetailType type;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Questions question;
}
