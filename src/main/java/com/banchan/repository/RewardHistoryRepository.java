package com.banchan.repository;

import com.banchan.model.entity.RewardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {

    @Query("SELECT sum(r.reward) FROM RewardHistory r WHERE r.userId = :userId")
    Double sumRewardByUserId(@Param("userId") Long userId);
}
