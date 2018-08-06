package com.banchan.repository;

import com.banchan.model.entity.QuestionsSingular;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsSingularRepository extends JpaRepository<QuestionsSingular, Integer> {
//
//    @Query(
//            value = "SELECT q FROM QuestionsSingular q "
//    )
//    public List<QuestionsSingular> findIncludeVoteCount(List<Integer> questions);
}
