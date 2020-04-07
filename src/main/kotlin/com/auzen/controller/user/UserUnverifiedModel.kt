package com.auzen.controller.user

import com.auzen.model.ErrorResponseModel

class UserUnverifiedModel(val verifyToken: String? = null)
    : ErrorResponseModel(
        type = "User-403",
        message = "Account registered, but not yet verified.")