package com.auzen.service

import com.auzen.model.UserModel
import com.auzen.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService


class UserAuthService(val userRepository: UserRepository) : UserDetailsService {
    val roles = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository?.getUserModelByAccountName(username.orEmpty())
        return UserDetail(user, roles)
    }

    class UserDetail : org.springframework.security.core.userdetails.User {
        constructor(o: UserModel?, roles: MutableCollection<out GrantedAuthority>?)
                : super(o?.accountName, o?.password, roles) {
        }
    }
}