package com.concertmania.api.member.adapters.out

import com.concertmania.api.member.domain.Member

object MemberMapper {
    fun Member.toEntity(): MemberEntity {
        return MemberEntity.create(
            name = name,
            email = email,
            password = password,
            roleType = roleType
        )
    }
    fun MemberEntity.toDomain(): Member {
        return Member.of(
            id = id ?: error("Member ID is not initialized"),
            name = name,
            email = email,
            password = password,
            roleType = roleType
        )
    }
}