package com.auzen.controller.user

import com.auzen.model.ErrorResponseModel

class UserUnauthorizedModel(type: String = "User-401")
    : ErrorResponseModel(
        type = type,
        message = "Unauthorized account, please verify your credential or contact our service.")