package com.concertmania.api.reservation.domain.model

import com.concertmania.api.common.exception.EntityNotPersistedException
import java.time.LocalDateTime

class Reservation private constructor(
    private val _id: Long? = null,
    val concertId: Long,
    val seatId: Long,
    val userId: Long,
    status: ReservationStatus,
    val reservedAt: LocalDateTime = LocalDateTime.now()
) {
    val id: Long
        get() = _id ?: throw EntityNotPersistedException(this::class.simpleName)
    var status: ReservationStatus = status
        protected set

    companion object {
        fun of(
            id: Long?,
            concertId: Long,
            seatId: Long,
            userId: Long,
            status: ReservationStatus
        ): Reservation {
            return Reservation(id, concertId, seatId, userId, status)
        }

        fun create(concertId: Long, seatId: Long, memberId: Long): Reservation {
            return Reservation(
                concertId = concertId,
                seatId = seatId,
                userId = memberId,
                status = ReservationStatus.PENDING
            )
        }

    }
}
