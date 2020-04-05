package com.auzen.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
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
        val token = request.getHeader(SecurityConstants.TOKEN_HEADER)
        if (!token.isNullOrEmpty() && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                val signingKey = SecurityConstants.JWT_SECRET.toByteArray()
                val parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""))
                val username = parsedToken
                        .getBody()
                        .getSubject()
                val authorities = (parsedToken.getBody()
                        .get("rol") as List<*>).stream()
                        .map { authority: Any? -> SimpleGrantedAuthority(authority as String?) }
                        .collect(Collectors.toList<GrantedAuthority>())
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
    }
}