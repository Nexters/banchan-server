package com.banchan.service.reviews;

import com.banchan.model.dto.reviews.ReviewReportRequestDto;
import com.banchan.model.dto.ReviewCountData;
import com.banchan.model.dto.reviews.ReviewsResponseDto;
import com.banchan.model.dto.reviews.ReviewsSaveRequestDto;
import com.banchan.model.dto.reviews.ReviewsUpdateRequestDto;
import com.banchan.model.entity.Reviews;
import com.banchan.model.entity.User;
import com.banchan.repository.ReportsRepository;
import com.banchan.repository.ReviewsRepository;
import com.banchan.repository.UserRepository;
import com.banchan.service.question.VotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReviewsService {
    private ReviewsRepository reviewsRepository;
    private ReportsRepository reportsRepository;
    private UserRepository userRepository;

    private VotesService votesService;

    // 클라이언트에 1번에 전달해 줄 댓글의 갯수
    final static int REVIEWS_SIZE = 10;
    @Transactional(readOnly = true)
    public List<ReviewsResponseDto> findReviews(Long questionId, Integer lastReviewId) {
        //lastReviewId == 0 => 질문카드 댓글요청 첫 페이지일 경우 lastReviewId에 Integer.MAX_VALUE 대입
        lastReviewId = (lastReviewId == 0) ? Integer.MAX_VALUE : lastReviewId;
        return reviewsRepository.findReviews(questionId, lastReviewId, REVIEWS_SIZE)
                .map((x) -> new ReviewsResponseDto(x, votesService.getAnswer(questionId, x)))
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer save(ReviewsSaveRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).get();
        return reviewsRepository.save(dto.toEntity(user)).getId();
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

    /**
     * 신고 테이블에 저장. REPORT_MAX_SIZE 이상 신고되면 댓글 신고상태값 변경
     */
    final static int REPORT_MAX_SIZE = 10;
    @Transactional
    public Integer saveReport(ReviewReportRequestDto dto) {
        Integer reportId = reportsRepository.save(dto.toReviewReportEntity()).getId();
        if (reportsRepository.countByReviewId(dto.getReviewId()) >= REPORT_MAX_SIZE) {
            Reviews review = reviewsRepository.findById(dto.getReviewId()).get();
            review.report();
            reviewsRepository.save(review);
        }
        return reportId;
    }

    public boolean isOverlap(ReviewReportRequestDto dto) {
        return reportsRepository.countByUserIdAndReviewId(dto.getUserId(), dto.getReviewId()) >= 1;
    }

    public CompletableFuture<Map<Long, Long>> findReviewCount(List<Long> questionIds){
        return reviewsRepository.countByQuestionIdInGroupByQuestion(
                questionIds.stream().map(Long::intValue).collect(Collectors.toList()))
                .thenApply(reviewCountData -> reviewCountData.stream()
                        .collect(Collectors.toMap(
                                ReviewCountData::getQuestionId,
                                ReviewCountData::getReviewCount)));
    }
}
