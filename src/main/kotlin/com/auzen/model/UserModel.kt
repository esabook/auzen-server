package com.auzen.model

import org.springframework.data.annotation.Id

class UserModel(var account_name: String,
                var name: String? = account_name,
                var email: String) {

    @Id
    var id: String = Long.MIN_VALUE.toString()
    var verify_token: String? = hashCode().toString()
    var verified: Boolean = false
        set(value) {
            field = value; verify_token = null
        }
}