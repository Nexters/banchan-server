package com.banchan.repository

import com.banchan.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE deviceKey = :deviceKey AND useYn ='Y'")
    fun findByDeviceKey(@Param("deviceKey") deviceKey: String): User?
}