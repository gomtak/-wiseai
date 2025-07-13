package com.concertmania.api.reservation.adapters.out

import com.concertmania.api.reservation.domain.model.Reservation

object ReservationMapper {
    fun ReservationEntity.toDomain(): Reservation {
        return Reservation.of(
            id = this.id,
            concertId = this.concertId,
            seatId = this.seatId,
            userId = this.userId,
            status = this.status,
        )
    }

    fun Reservation.toEntity(): ReservationEntity {
        return ReservationEntity.create(
            userId = userId,
            concertId = concertId,
            seatId = seatId,
            status = status,
            reservedAt = reservedAt
        )
    }
}