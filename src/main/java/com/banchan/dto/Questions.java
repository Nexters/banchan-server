package com.banchan.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "type")
    private int type;

    @Column(name = "random_order")
    private int randomOrder;

    @Column(name="write_time")
    private LocalDateTime writeTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<QuestionDetails> questionDetails;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private List<Votes> votes;
}
