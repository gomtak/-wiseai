package com.concertmania.api.concert.adapters.dto

import java.time.LocalDateTime

data class UpdateConcertRequest(
    val title: String?,
    val place: String?,
    val dateTime: LocalDateTime?,
)
