package com.banchan.repository;

import com.banchan.model.entity.Reviews;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewsRepositoryTest {

    @Autowired
    ReviewsRepository reviewsRepository;

    @After
    public void cleanup() {
        reviewsRepository.deleteAll();
    }

//    @Test
//    public void 댓글_불러오기() {
//        //given
//        reviewsRepository.save(Reviews.builder()
//                .content("댓글내용")
//                .uesrId(1)
//                .questionId(1)
//                .build());
//        //when
//        List<Reviews> reviewsList = reviewsRepository.findAll();
//
//        //then
//        Reviews reviews = reviewsList.get(0);
//        assertThat(reviews.getContent(), is("댓글내용"));
//        assertThat(reviews.getQuestionId(), is(1));
//    }

}
