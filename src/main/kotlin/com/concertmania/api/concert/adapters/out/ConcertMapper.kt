package com.concertmania.api.concert.adapters.out

import com.concertmania.api.concert.adapters.out.SeatMapper.toDomain
import com.concertmania.api.concert.domain.model.Concert

object ConcertMapper {
    fun ConcertEntity.toDomain(): Concert {
        return Concert.of(
            id = id,
            title = title,
            place = place,
            dateTime = dateTime,
            reservationOpenAt = reservationOpenAt,
            reservationCloseAt = reservationCloseAt,
            seatGrades = seatGrades.map { it.toDomain() },
            seats = seats.map { it.toDomain() }
        )
    }

    fun Concert.toEntity(): ConcertEntity {
        return ConcertEntity.create(
            title = title,
            place = place,
            dateTime = dateTime,
            reservationOpenAt = reservationOpenAt,
            reservationCloseAt = reservationCloseAt
        )
    }


}