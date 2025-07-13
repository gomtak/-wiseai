package com.concertmania.api.reservation.application.service

import com.concertmania.api.common.redis.RedisSpinLock
import com.concertmania.api.concert.application.port.`in`.SeatUseCase
import com.concertmania.api.concert.application.service.QueueService
import com.concertmania.api.concert.application.service.QueueStatus
import com.concertmania.api.concert.application.service.UserOnly
import com.concertmania.api.member.application.port.`in`.MemberUseCase
import com.concertmania.api.member.domain.RoleType
import com.concertmania.api.reservation.application.port.out.ReservationPort
import com.concertmania.api.reservation.application.port.`in`.ReservationUseCase
import com.concertmania.api.reservation.domain.model.Reservation
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}


@Service
class ReservationService(
    private val reservationPort: ReservationPort,
    private val seatUseCase: SeatUseCase,
    private val memberUseCase: MemberUseCase,
    private val redisSpinLock: RedisSpinLock,
    private val queueService: QueueService,
) : ReservationUseCase {


    override fun getReservationsByConcertId(concertId: Long): List<Reservation> {
        return reservationPort.findAllByConcertId(concertId)
    }

    @UserOnly(roleType = "roleType")
    @Transactional
    override fun reserveSeat(
        concertId: Long,
        seatId: Long,
        userName: String,
        roleType: RoleType
    ) {
        // 입장 대기열 확인
        val queueStatus = queueService.getQueueStatus(concertId, userName)
        when (queueStatus) {
            is QueueStatus.IN_QUEUE -> {
                if (queueStatus.position != 1) {
                    throw IllegalStateException("아직 입장 순서가 아닙니다. 현재 대기열 위치: ${queueStatus.position}")
                }
            }

            QueueStatus.NOT_IN_QUEUE -> {
                throw IllegalStateException("대기열에 참여하지 않았습니다.")
            }
        }

        seatUseCase.validateSeatForReservation(concertId, seatId, userName, roleType)
        val member = memberUseCase.getMemberByEmail(userName)
        val reservation = Reservation.create(
            concertId,
            seatId,
            member.id,
        )
        val reservationId = reservationPort.save(reservation)
        // 10분내 결재
        redisSpinLock.lock(reservationId.toString(), member.id.toString(), 10 * 60 * 1000L)

        // 대기열에서 제거
        queueService.leaveQueue(concertId, userName)
        logger.info { "Reserved seatId: $seatId for concertId: $concertId by user: $userName with reservationId: $reservationId" }
    }

    override fun getReservationById(reservationId: Long): Reservation {
        TODO("Not yet implemented")
    }
}