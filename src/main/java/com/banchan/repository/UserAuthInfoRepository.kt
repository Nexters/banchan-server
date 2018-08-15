package com.banchan.repository

import com.banchan.model.entity.UserAuthInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserAuthInfoRepository : JpaRepository<UserAuthInfo, Long> {
//    @Query("SELECT COUNT(u) FROM UserAuthInfo u WHERE userId = :userId AND tokenKey = :tokenKey")
    fun countByUserIdAndTokenKey(@Param("userId") userId: Long, @Param("tokenKey") tokenKey: String): Long = 0
}