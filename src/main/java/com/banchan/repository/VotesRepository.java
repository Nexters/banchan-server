package com.banchan.repository;

import com.banchan.dto.Votes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotesRepository extends JpaRepository<Votes, Integer> {
}
