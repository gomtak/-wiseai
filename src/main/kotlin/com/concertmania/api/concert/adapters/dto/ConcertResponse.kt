package com.concertmania.api.concert.adapters.dto

import com.concertmania.api.concert.domain.model.Concert
import com.concertmania.api.concert.domain.model.Seat
import com.concertmania.api.concert.domain.model.SeatGrade
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ConcertResponse(
    val id: Long,
    val title: String,
    val place: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateTime: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val reservationOpenAt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val reservationCloseAt: LocalDateTime,
    val seats: List<Seat>,
) {
    companion object {
        fun of(concert: Concert): ConcertResponse {
            return ConcertResponse(
                id = concert.id,
                title = concert.title,
                place = concert.place,
                dateTime = concert.dateTime,
                reservationOpenAt = concert.reservationOpenAt,
                reservationCloseAt = concert.reservationCloseAt,
                seats = concert.seats,
            )
        }
    }
}