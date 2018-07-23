package com.banchan.DTO;

import com.banchan.domain.question.DetailType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class QuestionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private char type;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private Questions question;

}
