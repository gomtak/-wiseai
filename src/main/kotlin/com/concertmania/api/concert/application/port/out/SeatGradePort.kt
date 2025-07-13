package com.concertmania.api.concert.application.port.out

import com.concertmania.api.concert.domain.model.SeatGrade

interface SeatGradePort {
    fun saveAll(seatGrades: List<SeatGrade>): List<Long>
    fun findById(seatGradeId: Long): SeatGrade?

}
