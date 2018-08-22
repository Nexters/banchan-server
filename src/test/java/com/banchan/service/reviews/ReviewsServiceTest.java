package com.banchan.service.reviews;

import com.banchan.model.dto.reviews.ReviewsSaveRequestDto;
import com.banchan.model.entity.Reviews;
import com.banchan.model.entity.User;
import com.banchan.repository.ReviewsRepository;
import com.banchan.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewsServiceTest {

    static final Long 테스트에사용할유저_ID = Long.valueOf(26);

    @Autowired
    private ReviewsService reviewsService;
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanup() {}

    @Test
    public void Dto데이터가_Reviews테이블에_저장된다() {
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
    public void 댓글_조회가되는지_테스트해봅시다아() {
        //given


        //when


    }
}

