package com.banchan.repository;

import com.banchan.model.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
    @Query(value = "SELECT * FROM reviews " +
            "WHERE " +
            "question_id = :questionId " +
            "AND report_state = 0 " +
            "AND delete_state = 0 " +
            "AND id < :lastReviewId " +
            "ORDER BY created_at DESC " +
            "LIMIT :REVIEWS_SIZE ",
    nativeQuery = true)
    Stream<Reviews> findReviews(
            @Param("questionId") Integer questionId,
            @Param("lastReviewId") Integer lastReviewId,
            @Param("REVIEWS_SIZE") int REVIEWS_SIZE);
}
