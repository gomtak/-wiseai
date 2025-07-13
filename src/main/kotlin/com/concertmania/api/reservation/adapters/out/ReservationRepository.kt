package com.concertmania.api.reservation.adapters.out

import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<ReservationEntity, Long> {
    fun findAllByConcertId(concertId: Long): List<ReservationEntity>

}
