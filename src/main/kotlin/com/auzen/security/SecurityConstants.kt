package com.auzen.security


class SecurityConstants private constructor() {
    companion object {
        const val AUTH_LOGIN_URL = "/auth"
        val WHITELIST_URL = arrayOf(
                //swagger
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**",

                //public api
                "/user/auth",
                "/user/register",
                "/user/verify"
        )

        // Signing key for HS512 algorithm
        const val JWT_SECRET = "&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9-EGITSAPUTRA"

        // JWT token defaults
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_TYPE = "JWT"
        const val TOKEN_ISSUER = "secure-api"
        const val TOKEN_AUDIENCE = "secure-app"
    }

    init {
        throw IllegalStateException("Cannot create instance of static util class")
    }
}
