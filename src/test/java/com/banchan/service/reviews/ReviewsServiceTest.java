package com.banchan.service.reviews;

import com.banchan.model.dto.reviews.ReviewReportRequestDto;
import com.banchan.model.dto.reviews.ReviewsResponseDto;
import com.banchan.model.dto.reviews.ReviewsSaveRequestDto;
import com.banchan.model.dto.reviews.ReviewsUpdateRequestDto;
import com.banchan.model.entity.Reviews;
import com.banchan.model.entity.User;
import com.banchan.repository.ReportsRepository;
import com.banchan.repository.ReviewsRepository;
import com.banchan.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewsServiceTest {

    static final Long 테스트에사용할유저_ID = Long.valueOf(65);
    static final Long 테스트에사용할질문_ID = Long.valueOf(150);

    @Autowired
    private ReviewsService reviewsService;
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportsRepository reportsRepository;

    @After
    public void cleanup() {}

    @Test
    public void 댓글이_저장이_되는지_봅시다아() {
        //given
        ReviewsSaveRequestDto dto = ReviewsSaveRequestDto.builder()
                .questionId(Long.valueOf(99999999))
                .userId(테스트에사용할유저_ID)
                .content("test content")
                .build();

        //when
        Long reviewsId = reviewsService.save(dto);

        //then
        Reviews reviews = reviewsRepository.findById(reviewsId).get();
        assertThat(reviews.getUser().getId()).isEqualTo(테스트에사용할유저_ID);
        assertThat(reviews.getQuestionId()).isEqualTo(dto.getQuestionId());
        assertThat(reviews.getContent()).isEqualTo(dto.getContent());

        //테스트 종료 후 삭제
        reviewsRepository.delete(reviews);
    }

    @Test
    public void 댓글이_조회가_되는지_봅시다아() throws InterruptedException{
        //given
        User dummyUser = userRepository.findById(테스트에사용할유저_ID).get();
        Reviews testReviews1 = reviewsRepository.save(Reviews.builder()
                .questionId(테스트에사용할질문_ID)
                .content("test review1")
                .deleteState(0)
                .reportState(0)
                .user(dummyUser)
                .build());
        sleep(1000);
        Reviews testReviews2 = reviewsRepository.save(Reviews.builder()
                .questionId(테스트에사용할질문_ID)
                .content("test review2")
                .deleteState(0)
                .reportState(0)
                .user(dummyUser)
                .build());
        sleep(1000);
        Reviews testReviews3 = reviewsRepository.save(Reviews.builder()
                .questionId(테스트에사용할질문_ID)
                .content("test review3")
                .deleteState(0)
                .reportState(0)
                .user(dummyUser)
                .build());

        //when
        List<ReviewsResponseDto> list = reviewsService.findReviews(테스트에사용할질문_ID,
                Long.valueOf(testReviews3.getId()));

        //then
        assertThat(list.get(0).getId()).isEqualTo(testReviews2.getId());
        assertThat(list.get(1).getId()).isEqualTo(testReviews1.getId());

        //테스트 종료 후 삭제
        reviewsRepository.delete(testReviews1);
        reviewsRepository.delete(testReviews2);
        reviewsRepository.delete(testReviews3);
    }

    @Test
    public void 댓글이_수정이_되는지_봅시다아() {
        //given
        User dummyUser = userRepository.findById(테스트에사용할유저_ID).get();
        Reviews testReviews4 = reviewsRepository.save(Reviews.builder()
                .questionId(테스트에사용할질문_ID)
                .content("수정 전 content")
                .deleteState(0)
                .reportState(0)
                .user(dummyUser)
                .build());

        ReviewsUpdateRequestDto dto = ReviewsUpdateRequestDto.builder()
                .reviewId(testReviews4.getId())
                .content("수정 된 content")
                .build();

        //when
        reviewsService.update(dto);

        //then
        String updatedContent = reviewsRepository.findById(testReviews4.getId()).get().getContent();
        assertThat(updatedContent).isEqualTo("수정 된 content");

        //테스트 종료 후 삭제
        reviewsRepository.delete(testReviews4);
    }

    @Test
    public void 댓글이_삭제가_되는지_봅시다아() {
        //given
        User dummyUser = userRepository.findById(테스트에사용할유저_ID).get();
        Reviews testReviews5 = reviewsRepository.save(Reviews.builder()
                .questionId(테스트에사용할질문_ID)
                .content("삭제될 댓글이니 정을 주지 말자 ㅠ")
                .deleteState(0)
                .reportState(0)
                .user(dummyUser)
                .build());

        //when
        reviewsService.delete(testReviews5.getId());

        //then
        assertThat(1).isEqualTo(reviewsRepository.findById(testReviews5.getId()).get().getDeleteState());

        //테스트 종료 후 삭제
        reviewsRepository.delete(testReviews5);
    }

    @Test
    public void 댓글이_신고가_되는지_봅시다아() {
        //given
        User dummyUser = userRepository.findById(테스트에사용할유저_ID).get();
        Reviews testReviews6 = reviewsRepository.save(Reviews.builder()
                .questionId(테스트에사용할질문_ID)
                .content("에라이 #$&*#(@*$#%&#(@#%@(&(신고당할 content)")
                .deleteState(0)
                .reportState(0)
                .user(dummyUser)
                .build());

        ReviewReportRequestDto dto = ReviewReportRequestDto.builder()
                .reviewId(testReviews6.getId())
                .userId(테스트에사용할유저_ID)
                .build();

        //when
        Long savedReportId = reviewsService.saveReport(dto);

        //then
        assertThat(reportsRepository.findById(savedReportId)).isNotEqualTo(Optional.empty());

        //테스트 종료 후 삭제
        reportsRepository.deleteById(savedReportId);
        reviewsRepository.delete(testReviews6);
    }
}

