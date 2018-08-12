package com.banchan.service.user

import com.banchan.config.Cryption
import com.banchan.model.entity.User
import com.banchan.model.entity.UserAuthInfo
import com.banchan.repository.UserAuthInfoRepository
import com.banchan.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
open class UserService {
    @Autowired lateinit var userRepository : UserRepository
    @Autowired lateinit var userAuthInfoRepository : UserAuthInfoRepository

    @Transactional
    open fun save(userReq: User) : User {
        val token:String = Cryption.getEncSHA256(Cryption.SecurityCode())
        val user: User = userRepository.save(userReq)
        user.userAuthInfo = userAuthInfoRepository.save(UserAuthInfo(userId = user.id, tokenKey = token))
        return user
    }


}