package com.banchan.service.user

import com.banchan.config.Cryption
import com.banchan.model.dto.UserData
import com.banchan.model.entity.User
import com.banchan.model.entity.UserAuthInfo
import com.banchan.repository.NameWordsRepository
import com.banchan.repository.UserAuthInfoRepository
import com.banchan.repository.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
open class UserService (
        val userRepository : UserRepository,
        val userAuthInfoRepository : UserAuthInfoRepository,
        val nameWordsRepository : NameWordsRepository
){

    @Transactional
    open fun save(userReq: User) : User {
        val token :String = Cryption.getEncSHA256(Cryption.SecurityCode())
        val user: User = userRepository.save(userReq.copy(useYn = "Y", sex = userReq.sex.toUpperCase()))
        user.userAuthInfo = userAuthInfoRepository.save(UserAuthInfo(userId = user.id, tokenKey = token))
        return user
    }

    fun auth(userId:Long, tokkenKey:String): Boolean {
        return userAuthInfoRepository.countByUserIdAndTokenKey(userId, tokkenKey) > 0
    }

    fun find(userId: Long): UserData {
        return userRepository.findUserDataByUserId(userId);
    }
}