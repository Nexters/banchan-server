package com.banchan.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "votes_a")
public class VotesA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "question_id")
    private Integer questionId;
}
