package com.banchan.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "questions")
@Builder
public class QuestionsSingular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "random_order")
    private Integer randomOrder;

    @Column(name="write_time")
    private LocalDateTime writeTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<QuestionDetails> questionDetails;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private List<Votes> votes;
}
