package com.banchan.model.entity

import lombok.Builder
import java.time.LocalDateTime
import javax.persistence.*

@Builder
@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(name = "device_key", nullable = false)
        val deviceKey: String,

        @Column(name = "username_id")
        val usernameId: Long,

        @Column
        val age: Integer,
//
//        @Column
//        val color: String,

        @Column(name = "use_yn")
        val useYn: String = "Y",

        @Column
        val sex: String,

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime? = LocalDateTime.now(),

        @Column(name = "created_at")
        val createdAt: LocalDateTime? = LocalDateTime.now(),

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name="id", referencedColumnName = "username_id")
        val username: Username
) {
    @Transient
    var userAuthInfo: UserAuthInfo? = null
}