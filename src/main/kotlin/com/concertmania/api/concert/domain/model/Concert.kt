package com.concertmania.api.concert.domain.model

import com.concertmania.api.common.exception.EntityNotPersistedException
import com.concertmania.api.reservation.domain.model.Reservation
import com.concertmania.api.reservation.domain.model.ReservationStatus
import java.time.LocalDateTime

class Concert private constructor(
    private val _id: Long? = null,
    title: String,
    place: String,
    dateTime: LocalDateTime,
    reservationOpenAt: LocalDateTime,
    reservationCloseAt: LocalDateTime,
    private val _seatGrades: List<SeatGrade> = mutableListOf(),
    private val _seats: List<Seat> = mutableListOf(),
) {
    val id: Long
        get() = _id ?: throw EntityNotPersistedException(this::class.simpleName)
    var title: String = title
        protected set
    var place: String = place
        protected set
    var dateTime: LocalDateTime = dateTime
        protected set
    var reservationOpenAt: LocalDateTime = reservationOpenAt
        protected set
    var reservationCloseAt: LocalDateTime = reservationCloseAt
        protected set

    val seatGrads: List<SeatGrade>
        get() = _seatGrades.toList()
    val seats: List<Seat>
        get() = _seats.toList()

    fun update(
        title: String?, place: String?, dateTime: LocalDateTime?,
        reservationOpenAt: LocalDateTime?,
        reservationCloseAt: LocalDateTime?,
    ) {
        title?.let { this.title = it }
        place?.let { this.place = it }
        dateTime?.let { this.dateTime = it }
        reservationOpenAt?.let { this.reservationOpenAt = it }
        reservationCloseAt?.let { this.reservationCloseAt = it }
    }

    fun checkReservations(reservations: List<Reservation>) {
        val reservedIds = reservations
            .filter { it.status != ReservationStatus.CANCELLED }
            .map { it.seatId }
            .toSet()

        this.seats.forEach { seat ->
            if (seat.id in reservedIds) {
                seat.markReserved()
            }
        }
    }


    companion object {
        fun create(
            title: String,
            place: String,
            dateTime: LocalDateTime,
            reservationOpen: LocalDateTime,
            reservationClose: LocalDateTime,
        ): Concert {
            return Concert(
                title = title,
                place = place,
                dateTime = dateTime,
                reservationOpenAt = reservationOpen,
                reservationCloseAt = reservationClose
            )
        }

        fun of(
            id: Long?,
            title: String,
            place: String,
            dateTime: LocalDateTime,
            reservationOpenAt: LocalDateTime,
            reservationCloseAt: LocalDateTime,
            seats: List<Seat>,
            seatGrades: List<SeatGrade>
        ): Concert {
            return Concert(
                _id = id,
                title = title,
                place = place,
                dateTime = dateTime,
                reservationOpenAt = reservationOpenAt,
                reservationCloseAt = reservationCloseAt,
                _seats = seats,
                _seatGrades = seatGrades
            )
        }
    }
}
