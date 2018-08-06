package com.banchan.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "questions")
public class QuestionsSingular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "random_order")
    private Integer randomOrder;

    @OneToMany(fetch = FetchType.EAGER)
    private List<QuestionDetails> questionDetails;
}
