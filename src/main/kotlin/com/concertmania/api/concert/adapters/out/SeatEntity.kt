package com.concertmania.api.concert.adapters.out

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "seat")
class SeatEntity private constructor(
    val concertId: Long,
    val seatGradeId: Long,
    @Column(length = 1)
    val rowLabel: Char,
    val columnNumber: Int,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    companion object {
        fun create(
            concertId: Long,
            seatGradeId: Long,
            rowLabel: Char,
            columnNumber: Int
        ): SeatEntity {
            return SeatEntity(
                concertId = concertId,
                seatGradeId = seatGradeId,
                rowLabel = rowLabel,
                columnNumber = columnNumber
            )
        }
    }
}