package com.banchan.controller

import com.banchan.model.entity.User
import com.banchan.model.response.CommonResponse
import com.banchan.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/user")
@RestController
open class UserController (
        val userService : UserService
) {

    @ResponseBody
    @PostMapping
    fun save (@RequestBody user: User): CommonResponse<User>? {
        return CommonResponse.success(userService.save(user))
    }

    @ResponseBody
    @GetMapping("/names")
    fun findNameWords() {

    }

}