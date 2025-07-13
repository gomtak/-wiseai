package com.concertmania.api.auth.adapters.`in`

import com.concertmania.api.auth.adapters.dto.SignInRequest
import com.concertmania.api.auth.application.port.`in`.AuthUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val authUseCase: AuthUseCase
) {

    @Operation(summary = "사용자 로그인")
    @PostMapping("/signin")
    fun signIn(
        @RequestBody request: SignInRequest
    ) = ok(
        authUseCase.signIn(
            email = request.email,
            password = request.password
        )
    )
}