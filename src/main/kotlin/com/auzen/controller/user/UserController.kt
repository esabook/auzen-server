package com.auzen.controller.user

import com.auzen.model.UserModel
import com.auzen.repository.UserRepository
import com.auzen.security.SecurityConstants
import io.jsonwebtoken.Jwts
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("", "/info")
    fun getInfo(@RequestHeader(SecurityConstants.TOKEN_HEADER) token: String): Any {
        if (!token.isNullOrEmpty() && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                val signingKey = SecurityConstants.JWT_SECRET.toByteArray()
                val parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""))
                val username = parsedToken
                        .getBody()
                        .getSubject()
                val oldUser = userRepository.getUserModelByAccountName(username)
                oldUser?.let {
                    return it
                }
            } catch (ignore: Exception) {
                throw Exception(ignore.message)
            }
        }
        return "TODO"
    }

    @PostMapping("/auth")
    fun postAuth(@RequestBody data: AuthRequestModel): Any {
        val oldUser = userRepository.getUserModelByAccountName(data.username)
        oldUser?.let {
            with(it) {
                if (oldUser.verified)
                    return "post /auth?username=yours&password=yours"
                else if (oldUser.password.equals(data.password))
                    return "post /user/verify?token=${oldUser.verifyToken}&account_name=username"
            }
        }
        return ResponseEntity("TODO", HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/verify")
    fun postVerifyAccount(@RequestParam token: String, @RequestParam account_name: String): ResponseEntity<Any> {
        val oldUserModel = userRepository.getUserModelsByVerifyTokenAndAccountName(token, account_name)
        oldUserModel?.let {
            it.verified = true
            it.password = BCrypt.hashpw(it.password, BCrypt.gensalt())
            userRepository.save(oldUserModel)

            return ResponseEntity(it, HttpStatus.OK)
        }

        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/register")
    @ApiOperation("Register a new user account")
    fun postRegister(@RequestBody data: UserRegisterRequestModel): ResponseEntity<UserRegisterResponseModel> {

        val newUserModel = UserModel(
                accountName = data.account_name,
                password = data.password,
                email = data.email
        )

        val oldUserModel = userRepository.getUserModelByAccountName(data.account_name)
        val responseModel = UserRegisterResponseModel(
                account_name = data.account_name,
                registered = oldUserModel != null
        )

        if (responseModel.registered) {
            oldUserModel?.verified?.let { responseModel.verified = it }
        } else {
            newUserModel.verifyToken = data.hashCode().toString() + newUserModel.hashCode().toString()
            userRepository.save(newUserModel)
        }

        return ResponseEntity(responseModel, HttpStatus.OK)
    }
}