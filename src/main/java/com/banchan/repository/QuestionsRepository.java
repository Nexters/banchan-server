package com.banchan.repository;

import com.banchan.DTO.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

}
