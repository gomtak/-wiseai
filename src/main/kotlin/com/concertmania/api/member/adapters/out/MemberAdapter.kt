package com.concertmania.api.member.adapters.out

import com.concertmania.api.common.annotation.PersistenceAdapter
import com.concertmania.api.member.adapters.out.MemberMapper.toDomain
import com.concertmania.api.member.adapters.out.MemberMapper.toEntity
import com.concertmania.api.member.application.port.out.MemberPort
import com.concertmania.api.member.domain.Member

@PersistenceAdapter
class MemberAdapter(
    private val memberRepository: MemberRepository
) : MemberPort {

    override fun save(member: Member) {
        memberRepository.save(member.toEntity())
    }

    override fun findByEmail(email: String): Member? {
        return memberRepository.findByEmail(email)?.toDomain()
    }

    override fun findById(id: Long): Member? {
        return memberRepository.findById(id)
            .map { it.toDomain() }
            .orElseGet { null }
    }

}