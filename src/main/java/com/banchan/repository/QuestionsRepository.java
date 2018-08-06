package com.banchan.repository;

import com.banchan.model.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    @Query(value =
            "SELECT q.id, q.user_id, q.random_order " +
                    "FROM (SELECT id, user_id, random_order " +
                    "   FROM  questions " +
                    "   WHERE random_order > -1) q " +
                    "LEFT JOIN ( " +
                    "   (SELECT * FROM votes_a " +
                    "   WHERE user_id = 3) " +
                    "   UNION ALL " +
                    "   (SELECT * FROM votes_b " +
                    "   WHERE user_id = 3)) v " +
                    "   ON q.id = v.question_id " +
                    "WHERE v.question_id IS NULL " +
                    "ORDER BY q.id ASC " +
                    "LIMIT 50",
            nativeQuery = true
    )
    public List<Questions> findNotSelectedQuestions();
}
