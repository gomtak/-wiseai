package com.concertmania.api.auth.application.port.`in`

import com.concertmania.api.auth.adapters.dto.TokenResponse

interface AuthUseCase {
    fun signIn(email: String, password: String): TokenResponse

}
