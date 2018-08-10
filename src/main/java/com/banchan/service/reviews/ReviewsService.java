package com.banchan.service.reviews;

import com.banchan.model.dto.ReviewsResponseDto;
import com.banchan.model.dto.ReviewsSaveRequestDto;
import com.banchan.model.dto.ReviewsUpdateRequestDto;
import com.banchan.model.entity.Reviews;
import com.banchan.repository.ReviewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReviewsService {
    private ReviewsRepository reviewsRepository;

    // 클라이언트에 1번에 전달해 줄 댓글의 갯수
    final static int REVIEWS_SIZE = 10;
    @Transactional(readOnly = true)
    public List<ReviewsResponseDto> findReviews(Integer questionId, Integer lastReviewId) {
        return reviewsRepository.findReviews(questionId, lastReviewId, REVIEWS_SIZE)
                .map(ReviewsResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Integer save(ReviewsSaveRequestDto dto) {
        return reviewsRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public Integer update(ReviewsUpdateRequestDto dto) {
        Reviews reviews = reviewsRepository.findById(dto.getReviewId()).get();
        reviews.updateContent(dto.getContent());
        return reviewsRepository.save(reviews).getId();
    }

    @Transactional
    public Integer delete(int deleteReviewId) {
        Reviews deleteReview = reviewsRepository.findById(deleteReviewId).get();
        deleteReview.delete();
        return reviewsRepository.save(deleteReview).getId();
    }
}
