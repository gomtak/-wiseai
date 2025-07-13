package com.concertmania.api.concert.adapters.`in`

import com.concertmania.api.concert.adapters.dto.SeatResponse
import com.concertmania.api.concert.application.port.`in`.SeatUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/seat")
@RestController
class SeatController(
    private val seatUseCase: SeatUseCase
) {

    @Operation(summary = "좌석 정보 조회")
    @GetMapping("/{seatId}")
    fun getSeatById(@PathVariable("seatId") seatId: Long): ResponseEntity<SeatResponse> {
        return ok(seatUseCase.getSeat(seatId))
    }
}