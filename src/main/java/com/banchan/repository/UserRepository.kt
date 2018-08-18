package com.banchan.repository

import com.banchan.model.dto.UserData
import com.banchan.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE deviceKey = :deviceKey AND useYn ='Y'")
    fun findByDeviceKey(@Param("deviceKey") deviceKey: String): User?

    fun findByIdIn(ids: List<Long>): List<User>?

    @Query("SELECT NEW com.banchan.model.dto.UserData(u, SUM(r.reward)) FROM User u JOIN RewardHistory r ON u.id = r.userId WHERE u.id = :userId")
    fun findUserDataByUserId(@Param("userId") userId: Long): UserData
}