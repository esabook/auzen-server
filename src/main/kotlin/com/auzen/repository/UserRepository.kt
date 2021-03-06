package com.auzen.repository

import com.auzen.model.UserModel
import org.springframework.data.mongodb.repository.MongoRepository

public open interface UserRepository : MongoRepository<UserModel, String> {
    fun getUserModelByAccountName(accountName: String): UserModel?
    fun getUserModelsByVerifyTokenAndAccountName(token: String, accountName: String): UserModel?
    fun findFirstByAccountNameAndPassword(accountName: String, password: String): UserModel?
}