package com.concertmania.api.reservation.adapters.out

import com.concertmania.api.reservation.domain.model.ReservationStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "reservation")
class ReservationEntity private constructor(
    val userId: Long,
    val concertId: Long,
    val seatId: Long,
    status: ReservationStatus,
    val reservedAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var paymentId: Long? = null
        protected set

    @Enumerated(EnumType.STRING)
    var status: ReservationStatus = status
        protected set


    companion object {
        fun create(
            userId: Long,
            concertId: Long,
            seatId: Long,
            status: ReservationStatus,
            reservedAt: LocalDateTime
        ): ReservationEntity {
            return ReservationEntity(userId, concertId, seatId, status, reservedAt)
        }
    }

    fun update(
        paymentId: Long,
        status: ReservationStatus,
    ) {
        this.paymentId = paymentId
        this.status = status
    }
}