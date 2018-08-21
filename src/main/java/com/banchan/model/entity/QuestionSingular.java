package com.banchan.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@Builder
public class QuestionSingular{

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

    @OneToMany
    @JoinColumn(name = "question_id")
    private List<QuestionDetails> details;
}
