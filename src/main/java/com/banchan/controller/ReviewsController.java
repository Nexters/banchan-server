package com.banchan.controller;

import com.banchan.model.dto.reviews.ReportRequestDto;
import com.banchan.model.dto.reviews.ReviewsSaveRequestDto;
import com.banchan.model.dto.reviews.ReviewsUpdateRequestDto;
import com.banchan.model.response.CommonResponse;
import com.banchan.service.reviews.ReviewsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/reviews")
public class ReviewsController {

    private ReviewsService reviewsService;

    /**
     * 댓글 조회 (최신순으로 조회 REVIEWS_SIZE 만큼 전달)
     * @param questionId | 댓글이 달린 질문카드의 ID
     * @param lastReviewId | 클라이언트가 마지막으로 받은 댓글의 ID
     * @return
     */
    @GetMapping("question/{questionId}/last-review/{lastReviewId}")
    public CommonResponse<?> findReviews(@PathVariable("questionId") Integer questionId,
                                                @PathVariable("lastReviewId") Integer lastReviewId) {
        return CommonResponse.success(reviewsService.findReviews(questionId, lastReviewId));
    }

    /**
     * 댓글 작성
     * @param dto | 댓글 작성용 RequestDto (속성 : questionId, userId, content)
     */
    @PostMapping("")
    public CommonResponse<?> saveReview (@RequestBody ReviewsSaveRequestDto dto) {
        return CommonResponse.success(reviewsService.save(dto));
    }

    /**
     * 댓글 수정
     * @param dto | 댓글 수정용 RequestDto (속성 : reviewId, content)
     */
    @PutMapping("")
    public CommonResponse<?> updateReview (@RequestBody ReviewsUpdateRequestDto dto) {
        return CommonResponse.success(reviewsService.update(dto));
    }

    /**
     * 댓글 삭제 (실제 데이터 삭제가 아닌 deleteState 값만 0 -> 1 로 변경)
     * @param deleteReviewId | 삭제할 댓글 ID
     */
    @DeleteMapping("{deleteReviewId}")
    public CommonResponse<?> deleteReview (@PathVariable("deleteReviewId") int deleteReviewId) {
        return CommonResponse.success(reviewsService.delete(deleteReviewId));
    }

    /**
     * 댓글 신고 (신고 테이블에 저장하고 해당 질문에 신고가 REPORT_MAX_SIZE 이상이면 해당 댓글 조회 안됨)
     * @param dto | 댓글 신고용 RequestDto (속성 : userId, reviewId)
     */
    @PostMapping("report")
    public CommonResponse<?> reportReview (@RequestBody ReportRequestDto dto) {
        if (reviewsService.isOverlap(dto)) {
            return CommonResponse.fail("동일한 유저가 중복 신고");
        }
        return CommonResponse.success(reviewsService.saveReport(dto));
    }
}
