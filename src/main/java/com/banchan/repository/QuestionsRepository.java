package com.banchan.repository;

import com.banchan.model.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
//
//    @Query("SELECT NEW com.banchan.dto.QuestionCardData(q, SUM(CASE WHEN v.answer = :answerType THEN 1 ELSE 0 END), COUNT(v.id)) " +
//            "FROM QuestionsSingular q LEFT JOIN FETCH q.votes v GROUP BY q.id")
//    List<QuestionCardData> findAllQuestionCardData(@Param("answerType") boolean type);
}
