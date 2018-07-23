package com.banchan.DTO;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Votes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private Questions question;

    @Column(name = "answer")
    private int answer;
}
