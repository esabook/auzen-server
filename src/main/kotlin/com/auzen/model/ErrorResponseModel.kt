package com.auzen.model

import java.util.*

open class ErrorResponseModel(
        val timestamp: Date = Date(),
        val type: String? = null,
        val title: String? = null,
        val message: String? = null,
        val instance: String? = null) {

    fun asMap(): Map<String, ErrorResponseModel> {
        return mapOf("error" to this)
    }
}