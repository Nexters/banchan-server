package com.banchan.repository;

import com.banchan.model.entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Reports, Integer> {
    Integer countByReviewId(Integer reviewId);
    Integer countByUserIdAndReviewId(Integer userId, Integer reviewId);
    Integer countByQuestionId(Integer questionId);
    Integer countByUserIdAndQuestionId(Integer userId, Integer questionId);
}
