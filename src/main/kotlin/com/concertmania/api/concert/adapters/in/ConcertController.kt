package com.concertmania.api.concert.adapters.`in`

import com.concertmania.api.auth.adapters.`in`.jwt.JwtAuthenticationToken
import com.concertmania.api.concert.adapters.dto.ConcertResponse
import com.concertmania.api.concert.adapters.dto.CreateConcertRequest
import com.concertmania.api.concert.adapters.dto.UpdateConcertRequest
import com.concertmania.api.concert.application.port.`in`.ConcertUseCase
import com.concertmania.api.concert.application.service.ConcertService
import com.concertmania.api.concert.application.service.QueueService
import com.concertmania.api.concert.application.service.QueueStatus
import com.concertmania.api.concert.domain.model.Concert
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/concert")
@RestController
class ConcertController(
    private val concertUseCase: ConcertUseCase,
    private val queueService: QueueService
) {

    @Operation(summary = "concert 생성")
    @PostMapping
    fun createConcert(
        @RequestBody request: CreateConcertRequest,
        authentication: Authentication
    ): ResponseEntity<Unit> {
        val authToken = authentication as JwtAuthenticationToken
        return ok(
            concertUseCase.createConcert(
                authToken.roleType,
                request.title,
                request.place,
                request.dateTime,
            )
        )
    }

    // 콘서트 목록 조회
    @Operation(summary = "콘서트 목록 조회")
    @GetMapping
    fun pageConcerts(@RequestParam(required = false) keyword: String?, pageable: Pageable) =
        ok(concertUseCase.pageConcerts(keyword, pageable))

    // **콘서트 정보 관리** (제목, 날짜, 장소, 가격)
    @Operation(summary = "콘서트 정보 관리 (제목, 날짜, 장소, 가격)")
    @PostMapping("/{concertId}")
    fun updateConcert(
        @PathVariable("concertId") concertId: Long,
        @RequestBody request: UpdateConcertRequest
    ) = ok(
        concertUseCase.updateConcert(
            concertId,
            request.title,
            request.place,
            request.dateTime,
        )
    )

    @Operation(summary = "콘서트 상세 조회")
    @GetMapping("/{concertId}")
    fun getConcert(
        @PathVariable("concertId") concertId: Long
    ): ResponseEntity<ConcertResponse> {
        val concert = concertUseCase.getConcert(concertId)
        return ok(ConcertResponse.of(concert))
    }

    @Operation(summary = "콘서트 대기열 참여")
    @PostMapping("/{concertId}/queue")
    fun joinQueue(
        @PathVariable("concertId") concertId: Long,
        authentication: Authentication
    ): ResponseEntity<QueueStatus> {
        val authToken = authentication as JwtAuthenticationToken
        return ok(queueService.joinQueue(concertId, authToken.userName, authToken.roleType))
    }

    @Operation(summary = "콘서트 대기열 상태 조회")
    @GetMapping("/{concertId}/queue/status")
    fun getQueueStatus(
        @PathVariable("concertId") concertId: Long,
        authentication: Authentication
    ): ResponseEntity<QueueStatus> {
        val authToken = authentication as JwtAuthenticationToken
        return ok(queueService.getQueueStatus(concertId, authToken.userName))
    }

}