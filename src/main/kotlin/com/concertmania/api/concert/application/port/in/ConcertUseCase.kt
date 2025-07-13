package com.concertmania.api.concert.application.port.`in`

import com.concertmania.api.concert.adapters.dto.SimpleConcertResponse
import com.concertmania.api.concert.application.service.ConcertService
import com.concertmania.api.concert.domain.model.Concert
import com.concertmania.api.member.domain.RoleType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.time.LocalDateTime

interface ConcertUseCase {
    fun pageConcerts(keyword: String?, pageable: Pageable): Page<SimpleConcertResponse>
    fun updateConcert(concertId: Long, title: String?, place: String?, dateTime: LocalDateTime?)
    fun createConcert(roleType: RoleType, title: String, place: String, dateTime: LocalDateTime)
    fun getConcert(concertId: Long): Concert
}
