package com.banchan.repository;

import com.banchan.model.entity.QuestionCardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionCardDataRepository extends JpaRepository<QuestionCardData, Long> {

    @Query(
            value = "SELECT qu.*, qd.type detail_type, qd.content detail_content FROM (  " +
                    "  SELECT q.*, u.prefix, u.postfix,  " +
                    "    COUNT(DISTINCT r.id) reviews,  " +
                    "    SUM(CASE WHEN answer = 0 THEN 1 ELSE 0 END) a,  " +
                    "    SUM(CASE WHEN answer = 1 THEN 1 ELSE 0 END) b  " +
                    "  FROM (  " +
                    "    SELECT q.*  " +
                    "    FROM questions q  " +
                    "    LEFT JOIN (SELECT question_id FROM votes WHERE user_id = 0) v  " +
                    "      ON q.id = v.question_id  " +
                    "    WHERE v.question_id IS NULL AND  " +
                    "      q.random_order > -1 AND  " +
                    "      q.decision IS NULL AND  " +
                    "      q.report_state = 0 AND  " +
                    "      q.user_id != 0  " +
                    "  ) q  " +
                    "  LEFT JOIN votes v  " +
                    "    ON q.id = v.question_id  " +
                    "  LEFT JOIN reviews r  " +
                    "    ON q.id = r.question_id  " +
                    "  LEFT JOIN usernames u  " +
                    "    ON q.user_id = u.id  " +
                    "  GROUP BY q.id) qu  " +
                    "LEFT JOIN question_details qd  " +
                    "  ON qu.id = qd.question_id",
            nativeQuery = true
    )
    List<QuestionCardData> findNotVotedQuestions();
}
