package com.banchan.repository

import com.banchan.model.entity.NameWords
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface NameWordsRepository : JpaRepository<NameWords, Long> {

    @Query(nativeQuery = true,
            value = "SELECT n.* " +
                    "FROM name_words n " +
                    "WHERE n.type = :type " +
                    "order by rand() " +
                    "LIMIT 1 ")
    fun findByType(@Param("type") type: String) : NameWords
}