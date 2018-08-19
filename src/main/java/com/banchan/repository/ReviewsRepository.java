package com.banchan.repository;

import com.banchan.model.dto.ReviewCountData;
import com.banchan.model.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
    @Query(value = "SELECT * FROM reviews " +
            "WHERE " +
            "question_id = :questionId " +
            "AND delete_state = 0 " +
            "AND id < :lastReviewId " +
            "ORDER BY created_at DESC " +
            "LIMIT :REVIEWS_SIZE ",
    nativeQuery = true)
    Stream<Reviews> findReviews(
            @Param("questionId") Long questionId,
            @Param("lastReviewId") Integer lastReviewId,
            @Param("REVIEWS_SIZE") int REVIEWS_SIZE);

    @Query("SELECT NEW com.banchan.model.dto.ReviewCountData(r.questionId, COUNT(r)) FROM Reviews r WHERE r.questionId IN (:questionIds) GROUP BY r.questionId")
    CompletableFuture<List<ReviewCountData>> countByQuestionIdInGroupByQuestion(@Param("questionIds") List<Long> questionIds);

}
