package com.banchan.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Votes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "question_id", nullable = false)
    private int questionId;

    @Column(name = "answer", nullable = false)
    private int answer;
}
