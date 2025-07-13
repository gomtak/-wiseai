package com.concertmania.api.reservation.adapters.`in`

import com.concertmania.api.auth.adapters.`in`.jwt.JwtAuthenticationToken
import com.concertmania.api.reservation.adapters.dto.ReservationRequest
import com.concertmania.api.reservation.application.port.`in`.ReservationUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reservation")
class ReservationController(
    private val reservationUseCase: ReservationUseCase,
) {
    @Operation(summary = "콘서트 좌석 예매")
    @PostMapping
    fun reserveConcert(
        @RequestBody request: ReservationRequest,
        authentication: Authentication
    ): ResponseEntity<Unit> {
        val authToken = authentication as JwtAuthenticationToken
        return ok(reservationUseCase.reserveSeat(request.concertId, request.seatId, authToken.userName, authToken.roleType))
    }
}