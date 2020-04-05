package com.auzen.controller.user

class UserRegisterRequestModel(
        val name: String? = null,
        val account_name: String,
        val password: String,
        val email: String
)