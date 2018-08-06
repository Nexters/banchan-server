package com.banchan.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

//@NamedNativeQuery(
//        name = "QuestionsSingular.findNotSelectedQuestions",
//        query = "SELECT q.id, q.user_id, q.random_order " +
//                "FROM (SELECT id, user_id, random_order " +
//                "   FROM  questions " +
//                "   WHERE random_order > -1) q " +
//                "LEFT JOIN ( " +
//                "   (SELECT * FROM votes_a " +
//                "   WHERE user_id = 2) " +
//                "   UNION ALL " +
//                "   (SELECT * FROM votes_b " +
//                "   WHERE user_id = 2)) v " +
//                "   ON q.id = v.question_id " +
//                "WHERE v.question_id IS NULL " +
//                "ORDER BY q.id " +
//                "LIMIT 50"
//)

@Data
@Entity
@Table(name = "questions")
public class QuestionsSingular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "random_order")
    private Integer randomOrder;

    @OneToMany
    @JoinColumn(name = "question_id")
    private List<QuestionDetails> questionDetails;
}
