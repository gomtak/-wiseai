package com.concertmania.api.concert.application.service

import com.concertmania.api.concert.adapters.dto.SimpleConcertResponse
import com.concertmania.api.concert.application.port.`in`.ConcertUseCase
import com.concertmania.api.concert.application.port.out.ConcertPort
import com.concertmania.api.concert.application.port.out.SeatGradePort
import com.concertmania.api.concert.application.port.out.SeatPort
import com.concertmania.api.concert.domain.model.Concert
import com.concertmania.api.concert.domain.service.SeatInitService
import com.concertmania.api.member.domain.RoleType
import com.concertmania.api.reservation.application.port.`in`.ReservationUseCase
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ConcertService(
    private val concertPort: ConcertPort,
    private val seatInitService: SeatInitService,
    private val seatGradePort: SeatGradePort,
    private val seatPort: SeatPort,
    private val reservationUseCase: ReservationUseCase,
) : ConcertUseCase {

    override fun pageConcerts(
        keyword: String?, pageable: Pageable
    ): Page<SimpleConcertResponse> {
        return concertPort.pageConcerts(keyword, pageable)
    }

    @Transactional
    override fun updateConcert(
        concertId: Long,
        title: String?,
        place: String?,
        dateTime: LocalDateTime?,
    ) {
        val concert = concertPort.findById(concertId) ?: throw EntityNotFoundException("concert not found")
        concert.update(
            title, place, dateTime, LocalDateTime.now(), LocalDateTime.now().plusDays(7)
        )
        concertPort.update(concert)
    }

    @AdminOnly(roleType = "roleType")
    @Transactional
    override fun createConcert(
        roleType: RoleType,
        title: String,
        place: String,
        dateTime: LocalDateTime,
    ) {
        val concert = Concert.create(
            title, place, dateTime, dateTime.minusMonths(1), dateTime
        )
        val concertId = concertPort.save(concert)

        val seatGrades = seatInitService.initSeatGrade(concertId)
        val seatGradeIds = seatGradePort.saveAll(seatGrades)
        val seats = seatInitService.initSeat(concertId, seatGradeIds)
        seatPort.saveAll(seats)
    }

    override fun getConcert(concertId: Long): Concert {
        val concert = concertPort.findById(concertId)
            ?: throw EntityNotFoundException("concert not found")
        val reservations = reservationUseCase.getReservationsByConcertId(concertId)
        concert.checkReservations(reservations)
        return concert
    }

    @PostConstruct
    fun init() {
        val title = "Sample Concert" + " - " + System.currentTimeMillis().toString().substring(8)
        createConcert(
            RoleType.ROLE_ADMIN,
            title,
            "Sample Place",
            LocalDateTime.now().plusWeeks(1),
        )
    }


}