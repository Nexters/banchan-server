package com.banchan.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId")
    private int userId;

    @Column(name="writeTime")
    private LocalDateTime writeTime;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<QuestionDetails> questionDetails;

    @OneToMany(mappedBy = "question")
    private List<Votes> votes;
}
