package com.banchan.repository;

import com.banchan.model.entity.QuestionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface QuestionDetailsRepository extends JpaRepository<QuestionDetails, Integer> {

    CompletableFuture<List<QuestionDetails>> findALLByQuestionIdInOrderByQuestionIdAsc(List<Integer> questionIds);
}
