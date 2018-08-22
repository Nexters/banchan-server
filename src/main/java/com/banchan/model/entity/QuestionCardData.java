package com.banchan.model.entity;

import com.banchan.model.domain.question.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
public class QuestionCardData{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "random_order")
    private Integer randomOrder;

    @Column(name = "type")
    @Convert(converter = QuestionTypeAttributeConverter.class)
    private QuestionType type;

    @Column(name = "decision")
    @Convert(converter = AnswerTypeAttributeConverter.class)
    private AnswerType decision;

    @JsonIgnore
    @Column(name = "write_time")
    private LocalDateTime writeTime;

    @Column(name = "report_state")
    private Integer reportState;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "postfix")
    private String postfix;

    @Column(name = "reviews")
    private Long reviews;

    @Column(name = "a")
    private Long countA;

    @Column(name = "b")
    private Long countB;

    @Column(name = "detail_type")
    @Convert(converter = DetailTypeAttributeConverter.class)
    private DetailType detailType;

    @Column(name = "detail_content")
    private String detailContent;
}
