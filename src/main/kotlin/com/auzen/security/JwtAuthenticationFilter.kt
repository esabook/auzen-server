package com.auzen.security

import com.auzen.service.UserAuthService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter : UsernamePasswordAuthenticationFilter {

    companion object {
        val tokenLifeTime = TimeUnit.HOURS.toMillis(12)
        fun getJWTToken(username: String): String? {
            val roles = UserAuthService.roles
            val token = Jwts.builder()
                    .signWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.toByteArray()), SignatureAlgorithm.HS512)
                    .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                    .setIssuer(SecurityConstants.TOKEN_ISSUER)
                    .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                    .setSubject(username)
                    .setExpiration(Date(System.currentTimeMillis() + tokenLifeTime))
                    .claim("rol", roles)
                    .compact()
            return "${SecurityConstants.TOKEN_PREFIX} $token"
        }
    }

    constructor(authenticationManager: AuthenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    init {
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL)
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse,
                                          filterChain: FilterChain?, authentication: Authentication) {
        val user = authentication.getPrincipal() as User
        val token = getJWTToken(user.username)
        response.addHeader(SecurityConstants.TOKEN_HEADER, token)
    }


}
