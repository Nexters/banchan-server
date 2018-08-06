package com.banchan.repository;

import com.banchan.model.entity.Questions;
import com.banchan.model.entity.VotesA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VotesARepository extends JpaRepository<VotesA, Integer> {

    @Query("SELECT COUNT(v) FROM VotesA v WHERE v.question IN (:questionsList) GROUP BY v.question ORDER BY v.question.id ASC")
    CompletableFuture<List<Long>> countByQuestionInGroupByQuestion(List<Questions> questionsList);
}
