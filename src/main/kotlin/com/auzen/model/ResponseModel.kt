package com.auzen.model

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class ResponseModel(val status: Boolean = false,
                         val message: String? = null,
                         val data: Any?,
                         httpCode: HttpStatus = HttpStatus.OK)
    : ResponseEntity<ResponseModel>(httpCode) {

}