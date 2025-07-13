package com.concertmania.api.auth.adapters.out.security

import com.concertmania.api.member.domain.RoleType
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val id: Long,
    val email: String,
    private var password: String?,
    val roleType: RoleType
) : UserDetails, CredentialsContainer {

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return setOf(SimpleGrantedAuthority(roleType.name))
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return email
    }

    override fun eraseCredentials() {
        password = null // 보안 강화
    }

}
