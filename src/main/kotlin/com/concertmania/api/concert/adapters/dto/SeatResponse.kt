package com.concertmania.api.concert.adapters.dto


data class SeatResponse(
    val id: Long,
    val seatNumber: String,
    val seatGrade: String,
    val price: Long,
) {


}
