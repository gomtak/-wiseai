package com.concertmania.api.reservation.adapters.dto

data class ReservationRequest(
    val concertId: Long,
    val seatId: Long,
)
