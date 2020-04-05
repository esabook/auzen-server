package com.auzen.controller.user

class AuthResponseModel(
        val access_token: String? = null,
        val refresh_token: String? = null,
        val verify_token: String? = null,
        val token_lifetime: Long = 0)