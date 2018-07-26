package com.banchan.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Votes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Questions question;

    @Column(name = "answer", nullable = false)
    private int answer;
}
