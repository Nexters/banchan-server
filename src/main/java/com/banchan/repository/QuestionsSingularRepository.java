package com.banchan.repository;

import com.banchan.model.entity.QuestionsSingular;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsSingularRepository extends JpaRepository<QuestionsSingular, Integer> {

//    @Query(value =
//            "SELECT * FROM (" +
//            "SELECT q.id, q.user_id, q.random_order " +
//            "FROM (SELECT id, user_id, random_order " +
//            "   FROM  questions " +
//            "   WHERE random_order > -1) q " +
//            "LEFT JOIN ( " +
//            "   (SELECT * FROM votes_a " +
//            "   WHERE user_id = 3) " +
//            "   UNION ALL " +
//            "   (SELECT * FROM votes_b " +
//            "   WHERE user_id = 3)) v " +
//            "   ON q.id = v.question_id " +
//            "WHERE v.question_id IS NULL " +
//            "ORDER BY q.id " +
//            "LIMIT 50) nq " +
//            "LEFT JOIN question_details qd" +
//            "ON nq.id = qd.question_id",
//            nativeQuery = true
//    )
//    public List<QuestionsSingular> findNotSelectedQuestions();
}
