package com.concertmania.api.member.adapters.dto

import com.concertmania.api.member.domain.RoleType

data class CreateMemberRequest(
    val name: String,
    val email: String,
    val password: String,
    val roleType: RoleType
)