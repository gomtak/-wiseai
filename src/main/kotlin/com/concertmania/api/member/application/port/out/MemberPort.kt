package com.concertmania.api.member.application.port.out

import com.concertmania.api.member.domain.Member

interface MemberPort {
    fun save(member: Member)

    fun findByEmail(email: String): Member?
    fun findById(id: Long): Member?
}