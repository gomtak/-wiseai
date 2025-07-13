package com.concertmania.api.concert.domain.model

import com.concertmania.api.common.exception.EntityNotPersistedException

data class SeatGrade(
    private val _id: Long? = null,
    val gradeName: Char,
    val price: Long,
    val concertId: Long
) {
    val id: Long
        get() = _id ?: throw EntityNotPersistedException(this::class.simpleName)
}