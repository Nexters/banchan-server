package com.banchan.model.entity

import lombok.Builder
import javax.persistence.*

@Builder
@Entity
@Table(name = "name_words")
data class NameWords(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = 0,

        @Column(name = "type")
        val type: String,

        @Column(name = "word")
        val word: String
)