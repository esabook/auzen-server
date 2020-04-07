package com.auzen.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("Account")
open class UserModel(
        /**
         * Id user berupa huruf yang mudah dipahami manusia.
         * bersifat unik.
         * dapat diubah-ubah.
         */
        @Indexed(name = "UserAccountName")
        var accountName: String,
        /**
         * password akses
         */
        var password: String,
        /**
         * email user yang dapat dihubungi
         */
        var email: String) {

    /**
     * bersifat unik
     */
    @Id
    var id: String = Long.MIN_VALUE.toString()

    /**
     * nama pengguna
     */
    var name: String? = accountName

    /**
     * jika akun belum pernah diverifikasi, field harus berisi token untuk verifikasi
     *
     */
    var verifyToken: String? = hashCode().toString()

    /**
     * akun dapat digunakan apabila field ini bernilai true
     */
    var verified: Boolean = false
        set(value) {
            field = value; verifyToken = null
        }
}