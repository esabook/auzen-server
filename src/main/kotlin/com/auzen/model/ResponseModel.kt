package com.auzen.model

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseModel<T>(status: HttpStatus = HttpStatus.OK) : ResponseEntity<T>(status) {
}