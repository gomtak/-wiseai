package com.concertmania.api.concert.application.port.out

import com.concertmania.api.concert.domain.model.Seat

interface SeatPort {
    fun saveAll(seats: MutableList<Seat>)
    fun findById(seatId: Long): Seat?

}
