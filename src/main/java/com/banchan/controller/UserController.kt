package com.banchan.controller

import com.banchan.config.annotation.BanchanAuth
import com.banchan.model.dto.UserData
import com.banchan.model.entity.NameWords
import com.banchan.model.entity.User
import com.banchan.model.response.CommonResponse
import com.banchan.service.user.NameService
import com.banchan.service.user.UserService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("api/users")
@RestController
open class UserController (
        val userService : UserService,
        val nameService: NameService
) {

    @ResponseBody
    @BanchanAuth
    @PostMapping
    fun save (@Valid @RequestBody user: User): CommonResponse<User>? {
        return CommonResponse.success(userService.save(user))
    }

    @ResponseBody
    @BanchanAuth
    @GetMapping("/names")
    fun findNameWords(): CommonResponse<List<NameWords>> {
        return CommonResponse.success(nameService.find())
    }

    @ResponseBody
    @BanchanAuth
    @GetMapping("/{userId}")
    fun findUser(@PathVariable("userId") userId: Long): CommonResponse<UserData> {
        return CommonResponse.success(userService.find(userId))
    }
}