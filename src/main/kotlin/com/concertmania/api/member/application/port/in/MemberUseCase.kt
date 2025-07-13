package com.concertmania.api.member.application.port.`in`

import com.concertmania.api.member.domain.Member
import com.concertmania.api.member.domain.RoleType

interface MemberUseCase {
    fun createMember(email: String, password: String, name: String, roleType: RoleType)
    fun getMemberByEmail(email: String): Member
}