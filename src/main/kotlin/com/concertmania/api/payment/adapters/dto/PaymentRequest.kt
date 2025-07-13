package com.concertmania.api.payment.adapters.dto

data class PaymentRequest(
    val reservationId: Long,
    val isSuccess: Boolean,
)
