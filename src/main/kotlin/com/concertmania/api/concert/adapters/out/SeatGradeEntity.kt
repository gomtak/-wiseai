package com.concertmania.api.concert.adapters.out

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "seat_grade")
class SeatGradeEntity private constructor(
    val concertId: Long,
    @Column(length = 1)
    val gradeName: Char,
    val price: Long,
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    companion object {
        fun create(
            concertId: Long,
            gradeName: Char,
            price: Long
        ): SeatGradeEntity {
            return SeatGradeEntity(concertId, gradeName, price)
        }
    }
}
