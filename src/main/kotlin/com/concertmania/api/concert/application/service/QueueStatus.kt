package com.concertmania.api.concert.application.service

sealed class QueueStatus {
    data class IN_QUEUE(
        val position: Int
    ) : QueueStatus()

    object NOT_IN_QUEUE : QueueStatus()
}