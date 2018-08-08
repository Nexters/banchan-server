package com.banchan.repository;

import com.banchan.model.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    @Query(value = "SELECT q.id, q.user_id, q.random_order, q.write_time " +
                    "FROM (SELECT id, user_id, random_order, write_time " +
                    "   FROM  questions " +
                    "   WHERE random_order > :randomOrder) q " +
                    "LEFT JOIN ( " +
                    "   (SELECT * FROM votes_a " +
                    "   WHERE user_id = :userId) " +
                    "   UNION ALL " +
                    "   (SELECT * FROM votes_b " +
                    "   WHERE user_id = :userId)) v " +
                    "   ON q.id = v.question_id " +
                    "WHERE v.question_id IS NULL " +
                    "ORDER BY q.id ASC " +
                    "LIMIT :limit",
            nativeQuery = true
    )
    public List<Questions> findNotSelectedQuestions(
            @Param("randomOrder") int lastOrder, @Param("userId") int userId, @Param("limit") int count);
}
