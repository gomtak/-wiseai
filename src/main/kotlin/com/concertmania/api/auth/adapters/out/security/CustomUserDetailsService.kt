package com.concertmania.api.auth.adapters.out.security

import com.concertmania.api.member.application.port.out.MemberPort
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberPort: MemberPort
) : UserDetailsService {

    override fun loadUserByUsername(username: String): CustomUserDetails {
        val member = memberPort.findByEmail(username)
            ?: throw EntityNotFoundException("User $username not found")
        return CustomUserDetails(
            id = member.id,
            email = member.email,
            password = member.password,
            roleType = member.roleType
        )
    }
}
