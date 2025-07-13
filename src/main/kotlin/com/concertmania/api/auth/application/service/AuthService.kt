package com.concertmania.api.auth.application.service

import com.concertmania.api.auth.adapters.dto.TokenResponse
import com.concertmania.api.auth.adapters.`in`.jwt.JwtProvider
import com.concertmania.api.auth.adapters.out.security.CustomUserDetails
import com.concertmania.api.auth.application.port.`in`.AuthUseCase
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProvider,
): AuthUseCase {

    override fun signIn(
        email: String,
        password: String
    ): TokenResponse {
        val authenticationToken =
            UsernamePasswordAuthenticationToken(email, password)
        val authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        val userDetails = authentication.principal as CustomUserDetails
        val token = jwtProvider.generateToken(userDetails)
        return token
    }
}