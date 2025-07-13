package com.concertmania.api.reservation.adapters.out

import com.concertmania.api.common.annotation.PersistenceAdapter
import com.concertmania.api.reservation.adapters.out.ReservationMapper.toDomain
import com.concertmania.api.reservation.adapters.out.ReservationMapper.toEntity
import com.concertmania.api.reservation.application.port.out.ReservationPort
import com.concertmania.api.reservation.domain.model.Reservation

@PersistenceAdapter
class ReservationAdapter(
    private val reservationRepository: ReservationRepository
): ReservationPort {
    override fun findAllByConcertId(concertId: Long): List<Reservation> {
        return reservationRepository.findAllByConcertId(concertId)
            .map { it.toDomain() }
    }

    override fun save(reservation: Reservation): Long {
        val entity = reservationRepository.save(reservation.toEntity())
        return entity.id ?: throw IllegalStateException("Failed to save reservation")
    }
}