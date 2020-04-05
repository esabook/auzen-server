package com.auzen.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Account")
open class UserModel(var accountName: String,
                     var password: String,
                     var email: String) {

    @Id
    var id: String = Long.MIN_VALUE.toString()
    var name: String? = accountName
    var verifyToken: String? = hashCode().toString()
    var verified: Boolean = false
        set(value) {
            field = value; verifyToken = null
        }
}