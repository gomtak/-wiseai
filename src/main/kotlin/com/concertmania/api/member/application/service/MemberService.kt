package com.concertmania.api.member.application.service

import com.concertmania.api.member.application.port.`in`.MemberUseCase
import com.concertmania.api.member.application.port.out.MemberPort
import com.concertmania.api.member.domain.Member
import com.concertmania.api.member.domain.RoleType
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberPort: MemberPort,
    private val passwordEncoder: PasswordEncoder
) : MemberUseCase {

    override fun createMember(email: String, password: String, name: String, roleType: RoleType) {
        val member = Member.create(
            name = name,
            email = email,
            password = passwordEncoder.encode(password),
            roleType = roleType
        )
        memberPort.save(member)
    }

    override fun getMemberByEmail(email: String): Member {
        return memberPort.findByEmail(email)
            ?: throw EntityNotFoundException("Member with email $email not found")
    }
}