package com.concertmania.api.concert.domain.model

import com.concertmania.api.common.exception.EntityNotPersistedException
import com.fasterxml.jackson.annotation.JsonIgnore

data class Seat(
    private val _id: Long? = null,
    val concertId: Long,
    val seatGradeId: Long,
    @JsonIgnore
    val rowLabel: Char,
    @JsonIgnore
    val columnNumber: Int,
) {
    val id: Long
        get() = _id ?: throw EntityNotPersistedException(this::class.simpleName)

    val seatNumber: String = "$rowLabel$columnNumber"

    var reserved: Boolean = false
        protected set

    fun markReserved() {
        this.reserved = true
    }

    fun validateSeatForReservation(concertId: Long) {
        if (this.concertId != concertId) {
            throw IllegalStateException("Seat with id $id does not belong to concert with id $concertId")
        }
        if (reserved) {
            throw IllegalStateException("Seat with id $id is already reserved")
        }
    }
}