package com.concertmania.api.concert.adapters.out

import com.concertmania.api.common.annotation.PersistenceAdapter
import com.concertmania.api.concert.adapters.out.SeatMapper.toDomain
import com.concertmania.api.concert.adapters.out.SeatMapper.toEntity
import com.concertmania.api.concert.application.port.out.SeatPort
import com.concertmania.api.concert.domain.model.Seat

@PersistenceAdapter
class SeatAdapter(
    private val seatRepository: SeatRepository
) : SeatPort {
    override fun saveAll(seats: MutableList<Seat>) {
        seatRepository.saveAll(seats.map { it.toEntity() })
    }

    override fun findById(seatId: Long): Seat? {
        return seatRepository.findById(seatId)
            .map { it.toDomain() }
            .orElseGet { null }
    }
}