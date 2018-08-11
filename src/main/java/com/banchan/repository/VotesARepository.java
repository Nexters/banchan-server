package com.banchan.repository;

import com.banchan.model.dto.VoteCountData;
import com.banchan.model.entity.VotesA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VotesARepository extends JpaRepository<VotesA, Integer> {

    @Query("SELECT NEW com.banchan.model.dto.VoteCountData(v.questionId, COUNT(v)) FROM VotesA v WHERE v.questionId IN (:questionIds) GROUP BY v.questionId")
    CompletableFuture<List<VoteCountData>> countByQuestionIdInGroupByQuestion(@Param("questionIds") List<Integer> questionIds);
}
