package com.concertmania.api.payment.adapters.`in`

import com.concertmania.api.auth.adapters.`in`.jwt.JwtAuthenticationToken
import com.concertmania.api.payment.adapters.dto.PaymentRequest
import com.concertmania.api.payment.application.service.PaymentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payment")
class PaymentController(
    private val paymentService: PaymentService,
) {

    @PostMapping
    @Operation(summary = "결제 처리")
    fun processPayment(
        request: PaymentRequest,
        authentication: Authentication
    ): ResponseEntity<Unit> {
        val authToken = authentication as JwtAuthenticationToken
        return ok(
            paymentService.processPayment(
                request.reservationId,
                request.isSuccess,
                authToken.userName,
                authToken.roleType
            )
        )
    }
}