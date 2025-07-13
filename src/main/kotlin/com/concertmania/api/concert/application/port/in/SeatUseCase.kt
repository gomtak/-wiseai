package com.concertmania.api.concert.application.port.`in`

import com.concertmania.api.concert.adapters.dto.SeatResponse
import com.concertmania.api.member.domain.RoleType

interface SeatUseCase {
    fun validateSeatForReservation(concertId: Long, seatId: Long, userName: String, roleType: RoleType)
    fun getSeat(seatId: Long): SeatResponse
}
