package com.concertmania.api.payment.application.service

import com.concertmania.api.concert.application.service.UserOnly
import com.concertmania.api.member.application.port.`in`.MemberUseCase
import com.concertmania.api.member.domain.RoleType
import com.concertmania.api.reservation.application.port.`in`.ReservationUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(
    private val memberUseCase: MemberUseCase,
    private val reservationUseCase: ReservationUseCase,
) {

    @Transactional
    @UserOnly(roleType = "roleType")
    fun processPayment(reservationId: Long, isSuccess: Boolean, userName: String, roleType: RoleType) {
        val member = memberUseCase.getMemberByEmail(userName)
        val reservation = reservationUseCase.getReservationById(reservationId)
        // redis 예매 Expired 여부 확인
        // 가격 정보 가져오기
        // 결재
        // 결재 완료 시 완료 이벤트 발송
        // 실패 시 실패 이벤트 발송
    }

}
