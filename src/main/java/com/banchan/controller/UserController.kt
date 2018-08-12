package com.banchan.controller

import com.banchan.model.entity.User
import com.banchan.model.response.CommonResponse
import com.banchan.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
open class UserController {
    @Autowired lateinit var userService : UserService

    @ResponseBody
    @PostMapping("")
    fun save (@RequestBody user: User): CommonResponse<User>? {
        return CommonResponse.success(user)
    }
}