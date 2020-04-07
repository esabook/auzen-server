package com.auzen.controller

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

open class ClientException(val msg: String, httpStatus: HttpStatus = HttpStatus.NOT_FOUND)
    : HttpClientErrorException(httpStatus)

