package com.auzen.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthorizationFilter(authenticationManager: AuthenticationManager?) : BasicAuthenticationFilter(authenticationManager) {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse,
                                  filterChain: FilterChain) {
        val authentication = getAuthentication(request)
        if (authentication == null) {
            filterChain.doFilter(request, response)
            return
        }
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = getToken(request)
        if (token != null) {
            try {
                val username = getUserName(token)
                val authorities = getAuthority(token)

                if (!username.isNullOrEmpty()) {
                    return UsernamePasswordAuthenticationToken(username, null, authorities)
                }
            } catch (exception: ExpiredJwtException) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.message)
            } catch (exception: UnsupportedJwtException) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.message)
            } catch (exception: MalformedJwtException) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.message)
            } catch (exception: SignatureException) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.message)
            } catch (exception: IllegalArgumentException) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.message)
            }
        }
        return null
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)
        private val signingKey = SecurityConstants.JWT_SECRET.toByteArray()
        private fun String.removeBearerPrefix() = this.replace("Bearer ", "")

        public fun getToken(request: HttpServletRequest): String? {
            val token = request.getHeader(SecurityConstants.TOKEN_HEADER)
            if (!token.isNullOrEmpty() && token.startsWith(SecurityConstants.TOKEN_PREFIX))
                return token;
            return null;
        }


        public fun getParsedToken(authToken: String): Jws<Claims>? =
                Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(authToken.removeBearerPrefix())

        public fun getUserName(authToken: String): String? =
                getParsedToken(authToken.removeBearerPrefix())
                        ?.getBody()
                        ?.getSubject()

        public fun getAuthority(authToken: String): MutableList<GrantedAuthority>? =
                (getParsedToken(authToken.removeBearerPrefix())
                        ?.getBody()
                        ?.get("rol") as List<*>)
                        .stream()
                        .map { authorityList: Any? -> (authorityList as LinkedHashMap<*, *>).get("authority") as String }
                        .map { authority: Any? -> SimpleGrantedAuthority(authority as String?) }
                        .collect(Collectors.toList<GrantedAuthority>())

    }
}