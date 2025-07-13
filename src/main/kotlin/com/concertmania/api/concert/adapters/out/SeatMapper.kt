package com.concertmania.api.concert.adapters.out

import com.concertmania.api.concert.domain.model.Seat
import com.concertmania.api.concert.domain.model.SeatGrade

object SeatMapper {
    fun Seat.toEntity(): SeatEntity {
        return SeatEntity.create(
            concertId = this.concertId,
            seatGradeId = this.seatGradeId,
            rowLabel = this.rowLabel,
            columnNumber = this.columnNumber,
        )
    }

    fun SeatGrade.toEntity(): SeatGradeEntity {
        return SeatGradeEntity.create(
            concertId = concertId,
            gradeName = gradeName,
            price = price,
        )
    }

    fun SeatGradeEntity.toDomain(): SeatGrade {
        return SeatGrade(
            _id = id,
            gradeName = gradeName,
            price = price,
            concertId = concertId
        )
    }

    fun SeatEntity.toDomain(): Seat {
        return Seat(
            _id = id,
            concertId = concertId,
            seatGradeId = seatGradeId,
            rowLabel = rowLabel,
            columnNumber = columnNumber
        )
    }
}