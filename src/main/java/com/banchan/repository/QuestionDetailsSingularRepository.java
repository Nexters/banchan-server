package com.banchan.repository;

import com.banchan.model.entity.QuestionDetailsSingular;
import com.banchan.model.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface QuestionDetailsSingularRepository extends JpaRepository<QuestionDetailsSingular, Integer> {

    @Query("SELECT qd FROM QuestionDetailsSingular qd WHERE qd.question IN (:questionsList) GROUP BY qd.question ORDER BY qd.question.id ASC")
    CompletableFuture<List<QuestionDetailsSingular>> findQuestionDetailsInGroupByQuestion(List<Questions> questionsList);

    CompletableFuture<List<QuestionDetailsSingular>> findALLByQuestionInOrderByQuestionAsc(List<Questions> questionsList);
}
