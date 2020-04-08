package com.auzen.controller.user

class UserRegisterRequestModel(
        val name: String? = null,
        val accountName: String,
        val password: String,
        val email: String
)