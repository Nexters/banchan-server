package com.banchan.repository

import com.banchan.model.entity.UserAuthInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserAuthInfoRepository : JpaRepository<UserAuthInfo, Long> {
    @Query("SELECT Count(u) FROM UserAuthInfo u WHERE userId = :userId AND tokenKey = :tokenKey AND useYn = 'Y'")
    fun countByUserIdAndTokenKey(userId: Long, tokenKey: String): Long
}