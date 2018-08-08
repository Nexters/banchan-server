package com.banchan.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "votes_b")
public class VotesB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "question_id")
    private Integer questionId;
}
