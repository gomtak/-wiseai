package com.concertmania.api.reservation.application.port.`in`

import com.concertmania.api.member.domain.RoleType
import com.concertmania.api.reservation.domain.model.Reservation

interface ReservationUseCase {
    fun getReservationsByConcertId(concertId: Long): List<Reservation>
    fun reserveSeat(concertId: Long, seatId: Long, userName: String, roleType: RoleType)
    fun getReservationById(reservationId: Long): Reservation
}