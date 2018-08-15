package com.banchan.controller

import com.banchan.model.entity.NameWords
import com.banchan.model.entity.User
import com.banchan.model.response.CommonResponse
import com.banchan.service.user.NameService
import com.banchan.service.user.UserService
import org.springframework.web.bind.annotation.*

@RequestMapping("api/users")
@RestController
open class UserController (
        val userService : UserService,
        val nameService: NameService
) {

    @ResponseBody
    @PostMapping
    fun save (@RequestBody user: User): CommonResponse<User>? {
        return CommonResponse.success(userService.save(user))
    }

    @ResponseBody
    @GetMapping("/names")
    fun findNameWords(): CommonResponse<List<NameWords>> {
        return CommonResponse.success(nameService.find());
    }

}