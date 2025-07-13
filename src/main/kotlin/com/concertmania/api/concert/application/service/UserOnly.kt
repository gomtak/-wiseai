package com.concertmania.api.concert.application.service

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserOnly(
    val roleType: String,
)
