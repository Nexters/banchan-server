package com.banchan.controller;

import com.banchan.config.annotation.BanchanAuth;
import com.banchan.model.dto.reviews.ReviewReportRequestDto;
import com.banchan.model.dto.reviews.ReviewsResponseDto;
import com.banchan.model.dto.reviews.ReviewsSaveRequestDto;
import com.banchan.model.dto.reviews.ReviewsUpdateRequestDto;
import com.banchan.model.response.CommonResponse;
import com.banchan.service.reviews.ReviewsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/reviews")
public class ReviewsController {

    private ReviewsService reviewsService;

    /**
     * 댓글 조회 (최신순으로 조회 REVIEWS_SIZE 만큼 전달)
     * @param questionId | 댓글이 달린 질문카드의 ID
     * @param lastReviewId | 클라이언트가 마지막으로 받은 댓글의 ID
     */
    @ApiOperation(value = "댓글 조회", notes = "해당 게시물의 댓글을 최신순, lastReviewId 이후의 댓글들을" +
            " REVIEWS_SIZE(10개) 조회 / answer: 0(작성자), 1(A or O 답변, 2(B or X 답변)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "질문카드 id",
                    required = true, dataType = "int", paramType = "path", defaultValue = ""),
            @ApiImplicitParam(name = "lastReviewId", value = "클라이언트가 마지막으로 전달받은 댓글의 id",
                    required = true, dataType = "int", paramType = "path", defaultValue = "")
    })
    @BanchanAuth
    @GetMapping("question/{questionId}/last-review/{lastReviewId}")
    public CommonResponse<List<ReviewsResponseDto>> findReviews(@PathVariable("questionId") Long questionId,
                                                                @PathVariable("lastReviewId") Long lastReviewId) {
        return CommonResponse.success(reviewsService.findReviews(questionId, lastReviewId));
    }

    /**
     * 댓글 작성
     * @param dto | 댓글 작성용 RequestDto (속성 : questionId, userId, content)
     */
    @ApiOperation(value = "댓글 작성", notes = "성공 시 작성한 댓글의 id값 반환")
    @BanchanAuth
    @PostMapping("")
    public CommonResponse<Long> saveReview (@RequestBody ReviewsSaveRequestDto dto) {
        return CommonResponse.success(reviewsService.save(dto));
    }

    /**
     * 댓글 수정
     * @param dto | 댓글 수정용 RequestDto (속성 : reviewId, content)
     */
    @ApiOperation(value = "댓글 수정", notes = "성공 시 수정한 댓글의 id값 반환")
    @BanchanAuth
    @PutMapping("")
    public CommonResponse<Long> updateReview (@RequestBody ReviewsUpdateRequestDto dto) {
        return CommonResponse.success(reviewsService.update(dto));
    }

    /**
     * 댓글 삭제 (실제 데이터 삭제가 아닌 deleteState 값만 0 -> 1 로 변경)
     * @param deleteReviewId | 삭제할 댓글 ID
     */
    @ApiOperation(value = "댓글 삭제", notes = "성공 시 삭제한 댓글의 id값 반환")
    @ApiImplicitParam(name = "deleteReviewId", value = "삭제할 댓글 id",
            required = true, dataType = "int", paramType = "path", defaultValue = "")
    @BanchanAuth
    @DeleteMapping("{deleteReviewId}")
    public CommonResponse<Long> deleteReview (@PathVariable("deleteReviewId") Long deleteReviewId) {
        return CommonResponse.success(reviewsService.delete(deleteReviewId));
    }

    /**
     * 댓글 신고 (신고 테이블에 저장하고 해당 질문에 신고가 REPORT_MAX_SIZE 이상이면 해당 댓글 조회 안됨)
     * @param dto | 댓글 신고용 RequestDto (속성 : userId, reviewId)
     */
    @PostMapping("report")
    @BanchanAuth
    @ApiOperation(value = "댓글 신고", notes = "성공 시 신고 고유 id 반환 // " +
            "동일한 유저가 동일한 댓글을 신고한 경우 fail | reason : isOverlap")
    public CommonResponse reportReview (@RequestBody ReviewReportRequestDto dto) {
        if (reviewsService.isOverlap(dto)) {
            return CommonResponse.fail("isOverlap");
        }
        return CommonResponse.success(reviewsService.saveReport(dto));
    }
}
