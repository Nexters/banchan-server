package com.banchan.repository;

import com.banchan.model.entity.QuestionCardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionCardDataRepository extends JpaRepository<QuestionCardData, Long> {

    @Query(
            value = "SELECT qu.*, qd.id detail_id, qd.type detail_type, qd.content detail_content FROM (  " +
                    "  SELECT q.*, u.prefix, u.postfix,  " +
                    "    COUNT(DISTINCT r.id) reviews,  " +
                    "    SUM(CASE WHEN answer = 0 THEN 1 ELSE 0 END) a,  " +
                    "    SUM(CASE WHEN answer = 1 THEN 1 ELSE 0 END) b  " +
                    "  FROM (  " +
                    "    SELECT q.*  " +
                    "    FROM questions q  " +
                    "    LEFT JOIN (SELECT question_id FROM votes WHERE user_id = :userId) v  " +
                    "      ON q.id = v.question_id  " +
                    "    WHERE v.question_id IS NULL AND  " +
                    "      q.random_order > :randomOrder AND  " +
                    "      q.decision IS NULL AND  " +
                    "      q.report_state = 0 AND  " +
                    "      q.user_id != :userId " +
                    "      LIMIT :counting " +
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
    List<QuestionCardData> findNotVotedQuestions(
            @Param("userId") Long userId, @Param("randomOrder") int lastOrder, @Param("counting") int count);

    @Query(
            value = "SELECT qu.*, qd.id detail_id, qd.type detail_type, qd.content detail_content " +
                    "FROM ( " +
                    "  SELECT q.*, u.prefix, u.postfix, " +
                    "    COUNT(DISTINCT r.id) reviews, " +
                    "    SUM(CASE WHEN answer = 0 THEN 1 ELSE 0 END) a, " +
                    "    SUM(CASE WHEN answer = 1 THEN 1 ELSE 0 END) b " +
                    "  FROM ( " +
                    "    SELECT q.*, v.vote_time " +
                    "    FROM questions q " +
                    "    JOIN (SELECT question_id, vote_time FROM votes WHERE user_id = :userId) v " +
                    "      ON q.id = v.question_id " +
                    "    WHERE report_state = 0 " +
                    "    ORDER BY v.vote_time DESC " +
                    "    LIMIT :start, :counting) q " +
                    "  LEFT JOIN votes v " +
                    "    ON q.id = v.question_id " +
                    "  LEFT JOIN reviews r " +
                    "    ON q.id = r.question_id " +
                    "  LEFT JOIN usernames u " +
                    "    ON q.user_id = u.id " +
                    "  GROUP BY q.id) qu " +
                    "LEFT JOIN question_details qd " +
                    "  ON qu.id = qd.question_id " +
                    "ORDER BY qu.vote_time DESC " ,
            nativeQuery = true
    )
    List<QuestionCardData> findVotedQuestions(
            @Param("userId") Long userId, @Param("start") int start, @Param("counting") int counting);

    @Query(
            value = "SELECT qu.*, qd.id detail_id, qd.type detail_type, qd.content detail_content " +
                    "FROM ( " +
                    "  SELECT q.*, u.prefix, u.postfix, " +
                    "    COUNT(DISTINCT r.id) reviews, " +
                    "    SUM(CASE WHEN answer = 0 THEN 1 ELSE 0 END) a, " +
                    "    SUM(CASE WHEN answer = 1 THEN 1 ELSE 0 END) b " +
                    "  FROM ( " +
                    "    SELECT * " +
                    "    FROM questions " +
                    "    WHERE user_id = :userId AND report_state = 0 " +
                    "    ORDER BY decision IS NULL DESC, id DESC " +
                    "    LIMIT :start, :counting) q " +
                    "  LEFT JOIN votes v " +
                    "    ON q.id = v.question_id " +
                    "  LEFT JOIN reviews r " +
                    "    ON q.id = r.question_id " +
                    "  LEFT JOIN usernames u " +
                    "    ON q.user_id = u.id " +
                    "  GROUP BY q.id) qu " +
                    "LEFT JOIN question_details qd " +
                    "  ON qu.id = qd.question_id " +
                    "ORDER BY qu.decision IS NULL DESC, qu.id DESC ",
            nativeQuery = true
    )
    List<QuestionCardData> findUserMadeQuestions(
            @Param("userId") Long userId, @Param("start") int start, @Param("counting") int counting);

    @Query(
            value = "SELECT qu.*, qd.id detail_id, qd.type detail_type, qd.content detail_content " +
                    "FROM ( " +
                    "  SELECT q.*, u.prefix, u.postfix, " +
                    "    COUNT(DISTINCT r.id) reviews, " +
                    "    SUM(CASE WHEN answer = 0 THEN 1 ELSE 0 END) a, " +
                    "    SUM(CASE WHEN answer = 1 THEN 1 ELSE 0 END) b " +
                    "  FROM ( " +
                    "    SELECT * " +
                    "    FROM questions " +
                    "    WHERE id = :questionId) q " +
                    "  LEFT JOIN votes v " +
                    "    ON q.id = v.question_id " +
                    "  LEFT JOIN reviews r " +
                    "    ON q.id = r.question_id " +
                    "  LEFT JOIN usernames u " +
                    "    ON q.user_id = u.id " +
                    "  GROUP BY q.id) qu " +
                    "LEFT JOIN question_details qd " +
                    "  ON qu.id = qd.question_id ",
            nativeQuery = true
    )
    List<QuestionCardData> findQuestion(
            @Param("questionId") Long questionId);
}
