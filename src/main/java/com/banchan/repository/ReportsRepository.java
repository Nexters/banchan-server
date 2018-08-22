package com.banchan.repository;

import com.banchan.model.entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Reports, Long> {
    Integer countByReviewId(Long reviewId);
    Integer countByUserIdAndReviewId(Long userId, Long reviewId);
    Integer countByQuestionId(Long questionId);
    Integer countByUserIdAndQuestionId(Long userId, Long questionId);
}
