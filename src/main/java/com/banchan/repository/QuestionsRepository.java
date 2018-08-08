package com.banchan.repository;

import com.banchan.model.entity.Questions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    @Query(value = "SELECT q.id, q.user_id, q.random_order, q.write_time, q.decision " +
                    "FROM (SELECT id, user_id, random_order, write_time, decision " +
                    "   FROM  questions " +
                    "   WHERE random_order > :randomOrder AND decision IS NULL) q " +
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
    List<Questions> findNotVotedQuestions(
            @Param("userId") int userId, @Param("randomOrder") int lastOrder, @Param("limit") int count);

    Page<Questions> findAllByUserIdOrderByDecisionAscIdDesc(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM questions q\n" +
            "  JOIN (\n" +
            "    (SELECT question_id, vote_time FROM votes_a\n" +
            "    WHERE user_id = 2)\n" +
            "    UNION ALL\n" +
            "    (SELECT question_id, vote_time FROM votes_b\n" +
            "    WHERE user_id = 2)) v\n" +
            "    ON q.id = v.question_id\n" +
            "ORDER BY v.vote_time DESC",
            nativeQuery = true
    )
    Page<Questions> findVotedQuestions(@Param("userId") int userId, Pageable pageable);
}
