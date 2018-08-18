package com.banchan.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import java.time.LocalDateTime
import javax.persistence.*

@Data
@Entity(name = "usernames")
data class Username (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(name = "prefix")
        val prefix: String,

        @Column(name = "postfix")
        val postfix: String,

        @Column(name = "updated_at", nullable = true)
        val updatedAt: LocalDateTime? = LocalDateTime.now(),

        @Column(name = "created_at")
        val createdAt: LocalDateTime? = LocalDateTime.now()
)