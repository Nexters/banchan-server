package com.banchan.repository

import com.banchan.model.entity.UserAuthInfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserAuthInfoRepository : JpaRepository<UserAuthInfo, Long> {
    fun findAllByUserIdOrderByIdDesc(userId: Long): List<UserAuthInfo>
}