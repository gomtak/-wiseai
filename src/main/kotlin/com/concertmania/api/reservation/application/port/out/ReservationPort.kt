package com.concertmania.api.reservation.application.port.out

import com.concertmania.api.reservation.domain.model.Reservation

interface ReservationPort {
    fun findAllByConcertId(concertId: Long): List<Reservation>
    fun save(reservation: Reservation): Long

}