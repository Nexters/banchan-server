package com.banchan.repository;

import com.banchan.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findAllByQuestionId(Long questionId);

    Vote findByQuestionIdAndUserId(Long questionId, Long userId);

    Long countAllByQuestionId(Long questionId);
}
