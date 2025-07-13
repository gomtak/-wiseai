package com.concertmania.api.concert.application.service

import com.concertmania.api.concert.adapters.dto.SeatResponse
import com.concertmania.api.concert.application.port.`in`.SeatUseCase
import com.concertmania.api.concert.application.port.out.SeatGradePort
import com.concertmania.api.concert.application.port.out.SeatPort
import com.concertmania.api.member.application.port.`in`.MemberUseCase
import com.concertmania.api.member.domain.RoleType
import com.concertmania.api.reservation.application.port.out.ReservationPort
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeatService(
    private val seatPort: SeatPort,
    private val seatGradePort: SeatGradePort,
    private val memberUseCase: MemberUseCase,
    private val reservationPort: ReservationPort
) : SeatUseCase {
    override fun getSeat(seatId: Long): SeatResponse {
        val seat = (seatPort.findById(seatId)
            ?: throw EntityNotFoundException("Seat with id $seatId not found"))
        val grade = (seatGradePort.findById(seat.seatGradeId)
            ?: throw EntityNotFoundException("Seat grade with id ${seat.seatGradeId} not found"))

        return SeatResponse(
            id = seat.id,
            seatNumber = seat.seatNumber,
            seatGrade = "${grade.gradeName}",
            price = grade.price,
        )
    }

    @Transactional
    @UserOnly(roleType = "roleType")
    override fun validateSeatForReservation(concertId: Long, seatId: Long, userName: String, roleType: RoleType) {
        val seat = seatPort.findById(seatId)
            ?: throw EntityNotFoundException("Seat with id $seatId not found")
        seat.validateSeatForReservation(concertId)
    }
}
