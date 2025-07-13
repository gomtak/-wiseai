package com.concertmania.api.auth.adapters.`in`.jwt

import com.concertmania.api.member.domain.RoleType
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

class JwtAuthenticationToken(
    val userName: String,
    val roleType: RoleType
) : AbstractAuthenticationToken(listOf(SimpleGrantedAuthority(roleType.name))) {

    init {
        isAuthenticated = true // JWT가 유효한 경우 인증된 상태로 설정
    }

    override fun getCredentials(): Any? = null // JWT 인증이므로 비밀번호 등 자격 증명 불필요

    override fun getPrincipal(): Any = userName //
}