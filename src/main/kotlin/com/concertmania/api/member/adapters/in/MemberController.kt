package com.concertmania.api.member.adapters.`in`

import com.concertmania.api.member.adapters.dto.CreateMemberRequest
import com.concertmania.api.member.application.port.`in`.MemberUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberUseCase: MemberUseCase
) {

    @Operation(summary = "사용자 회원가입")
    @PostMapping
    fun createUser(@RequestBody request: CreateMemberRequest) =
        ok(memberUseCase.createMember(request.email, request.password, request.name, request.roleType))
}