package com.auzen.controller.user

import com.auzen.controller.ClientException
import com.auzen.model.UserModel
import com.auzen.repository.UserRepository
import com.auzen.security.JwtAuthenticationFilter
import com.auzen.security.JwtAuthorizationFilter
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.*
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @RequestMapping("/info", method = [RequestMethod.GET])
    @ApiResponses(
            ApiResponse(code = 200, message = "", response = UserModel::class),
            ApiResponse(code = 417, message = "")
    )
    fun getInfo(req: HttpServletRequest): Any {
        val token = JwtAuthorizationFilter.getToken(req)
        token?.let {
            val username = JwtAuthorizationFilter.getUserName(it)
            username?.let {
                val oldUser = userRepository.getUserModelByAccountName(it)
                oldUser?.let {
                    return it
                }
            }
        }
        throw ClientException("Token isValid but account was not found", HttpStatus.EXPECTATION_FAILED)
    }

    @RequestMapping("/auth", method = [RequestMethod.POST])
    @ApiResponses(
            ApiResponse(code = 200, response = AuthResponseModel::class, message = ""),
            ApiResponse(code = 401, response = UserUnauthorizedModel::class, message = ""),
            ApiResponse(code = 403, response = UserUnverifiedModel::class, message = ""),
            ApiResponse(code = 404, message = "")
    )
    fun postAuth(@RequestBody data: AuthRequestModel): Any {
        val oldUser = userRepository.getUserModelByAccountName(data.username)
        oldUser?.let {
            with(it) {
                if (verified) {
                    val token: String? = JwtAuthenticationFilter.getJWTToken(accountName)
                    token?.let {
                        return AuthResponseModel(
                                token,
                                "not-set",
                                "not-set",
                                JwtAuthenticationFilter.tokenLifeTime
                        )
                    }
                } else if (password.equals(data.password)) {
                    return UserUnverifiedModel(verifyToken)

                } else {

                }
            }
        }
        return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/verify")
    fun postVerifyAccount(@RequestParam token: String, @RequestParam account_name: String): ResponseEntity<Any> {
        if (token.isNullOrEmpty() || token.length < 10)
            throw ClientException("Request info invalid", HttpStatus.BAD_REQUEST)

        val oldUserModel = userRepository.getUserModelsByVerifyTokenAndAccountName(token, account_name)
        oldUserModel?.let {
            it.verified = true
            it.password = BCrypt.hashpw(it.password, BCrypt.gensalt())
            userRepository.save(oldUserModel)

            return ResponseEntity(it, HttpStatus.OK)
        }

        throw ClientException("Verification not processed", HttpStatus.NOT_FOUND)
    }

    @PostMapping("/register")
    @ApiOperation("Register a new user account")
    fun postRegister(@RequestBody data: UserRegisterRequestModel): ResponseEntity<UserRegisterResponseModel> {

        with(data) {
            val badRequestCode = HttpStatus.BAD_REQUEST;
            if (accountName.isNullOrEmpty() || accountName.length < 4)
                throw ClientException("Account name must be set with minimum 4 characters", badRequestCode)
            if (password.isNullOrEmpty() || password.length < 4)
                throw ClientException("Password must be set with minimum 4 characters", badRequestCode)
            if (!Pattern.matches("^(.+)@(.+)$", email))
                throw ClientException("Email invalid", badRequestCode)
        }


        val newUserModel = UserModel(
                accountName = data.accountName,
                password = data.password,
                email = data.email
        )

        var oldUserModel = userRepository.getUserModelByAccountName(data.accountName)
        val responseModel = UserRegisterResponseModel(
                accountName = data.accountName,
                registered = oldUserModel != null
        )

        if (responseModel.registered) {
            oldUserModel?.verified?.let { responseModel.verified = it }
        } else {
            newUserModel.verifyToken = data.hashCode().toString() + newUserModel.hashCode().toString()
            userRepository.save(newUserModel)
        }
        oldUserModel = userRepository.getUserModelByAccountName(data.accountName)
        oldUserModel?.let {
            responseModel.registered = true
        }

        return ResponseEntity(responseModel, HttpStatus.OK)
    }
}