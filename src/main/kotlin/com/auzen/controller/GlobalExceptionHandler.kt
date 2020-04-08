package com.auzen.controller

import com.auzen.model.ErrorResponseModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun errorException(ex: Exception, req: WebRequest): ResponseEntity<Map<String, ErrorResponseModel>> {
        val errorDetail = ErrorResponseModel(
                type = "Default-500",
                title = "Internal Server Error",
                message = ex.message,
                instance = req.getDescription(false))

        return ResponseEntity(errorDetail.asMap(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ClientException::class)
    fun errorClientException(ex: ClientException, req: WebRequest): Any {
        val errorDetail = ErrorResponseModel(
                type = "Error-${ex.statusCode.value()}",
                title = ex.statusText,
                message = ex.msg,
                instance = req.getDescription(false))
        return ResponseEntity(errorDetail.asMap(), ex.statusCode)
    }


}