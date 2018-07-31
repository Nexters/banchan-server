package com.banchan.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Builder
public class QuestionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private int type;

    @Column(name = "content")
    private String content;

    @Column(name = "question_id")
    private int questionId;

}
