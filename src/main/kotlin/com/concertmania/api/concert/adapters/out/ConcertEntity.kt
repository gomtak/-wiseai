package com.concertmania.api.concert.adapters.out

import com.concertmania.api.concert.domain.model.Seat
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "concert")
class ConcertEntity private constructor(
    title: String,
    dateTime: LocalDateTime,
    place: String,
    reservationOpenAt: LocalDateTime,
    reservationCloseAt: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
    var title: String = title
        protected set
    var dateTime: LocalDateTime = dateTime
        protected set
    var place: String = place
        protected set

    var reservationOpenAt: LocalDateTime = reservationOpenAt
        protected set
    var reservationCloseAt: LocalDateTime = reservationCloseAt
        protected set

    @OneToMany(mappedBy = "concertId")
    val seats: MutableList<SeatEntity> = mutableListOf()

    @OneToMany(mappedBy = "concertId")
    val seatGrades: MutableList<SeatGradeEntity> = mutableListOf()

    fun update(
        title: String, place: String, date: LocalDateTime,
        reservationOpenAt: LocalDateTime,
        reservationCloseAt: LocalDateTime,
    ) {
        this.title = title
        this.place = place
        this.dateTime = date
        this.reservationOpenAt = reservationOpenAt
        this.reservationCloseAt = reservationCloseAt
    }

    companion object {
        fun create(
            title: String, dateTime: LocalDateTime, place: String,
            reservationOpenAt: LocalDateTime,
            reservationCloseAt: LocalDateTime,
        ): ConcertEntity {
            return ConcertEntity(
                title, dateTime, place,
                reservationOpenAt, reservationCloseAt
            )
        }
    }
}