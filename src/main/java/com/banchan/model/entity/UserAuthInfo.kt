package com.banchan.model.entity

import lombok.Data
import java.time.LocalDateTime
import javax.persistence.*

@Data
@Entity(name = "user_auth_infos")
data class UserAuthInfo (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(name = "user_id")
        val userId: Long,

        @Column(name = "token_key")
        val tokenKey: String,

        @Column(name = "use_yn")
        val useYn: String = "Y",

        @Column(name = "updated_at", nullable = true)
        val updatedAt: LocalDateTime? = LocalDateTime.now(),

        @Column(name = "created_at")
        val createdAt: LocalDateTime? = LocalDateTime.now()

)