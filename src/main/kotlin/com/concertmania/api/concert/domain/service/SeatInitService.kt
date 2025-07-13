package com.concertmania.api.concert.domain.service

import com.concertmania.api.concert.domain.model.Seat
import com.concertmania.api.concert.domain.model.SeatGrade
import org.springframework.stereotype.Service

@Service
class SeatInitService {
    fun initSeatGrade(concertId: Long): List<SeatGrade> {
        return listOf(
            SeatGrade(gradeName = 'A', price = 10000, concertId = concertId),
            SeatGrade(gradeName = 'S', price = 13000, concertId = concertId),
            SeatGrade(gradeName = 'R', price = 15000, concertId = concertId)
        )
    }

    fun initSeat(concertId: Long, seatGrades: List<Long>): MutableList<Seat> {
        val seats = mutableListOf<Seat>()
        for (row in listOf('A', 'B', 'C')) {
            for (col in 1..5) {
                val seat = Seat(
                    concertId = concertId,
                    seatGradeId = seatGrades[col % seatGrades.size],
                    rowLabel = row,
                    columnNumber = col,
                )
                seats.add(seat)
            }
        }
        return seats
    }
}