package com.banchan.repository;

import com.banchan.model.dto.QuestionCardData;
import com.banchan.model.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    @Query("SELECT NEW com.banchan.dto.QuestionCardData(q, SUM(CASE WHEN v.answer = :answerType THEN 1 ELSE 0 END), COUNT(v.id)) " +
            "FROM QuestionsSingular q LEFT JOIN FETCH q.votes v GROUP BY q.id")
    List<QuestionCardData> findAllQuestionCardData(@Param("answerType") int type);
}
