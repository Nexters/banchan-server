package com.banchan.repository;

import com.banchan.dto.QuestionCardData;
import com.banchan.dto.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

    static final int ANS_A = 0;
    static final int ANS_B = 1;

    @Query("SELECT NEW com.banchan.dto.QuestionCardData(q, SUM(CASE WHEN v.answer = " + ANS_A + " THEN 1 ELSE 0 END), COUNT(v.id)) " +
            "FROM Questions q LEFT JOIN q.votes v GROUP BY q.id")
    List<QuestionCardData> findAllQuestionCardData();

}
