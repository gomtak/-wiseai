package com.concertmania.api.concert.adapters.out

import com.concertmania.api.common.annotation.PersistenceAdapter
import com.concertmania.api.concert.adapters.out.SeatMapper.toDomain
import com.concertmania.api.concert.adapters.out.SeatMapper.toEntity
import com.concertmania.api.concert.application.port.out.SeatGradePort
import com.concertmania.api.concert.domain.model.SeatGrade

@PersistenceAdapter
class SeatGradeAdapter(
    private val seatGradeRepository: SeatGradeRepository
) : SeatGradePort {
    override fun saveAll(seatGrades: List<SeatGrade>): List<Long> {
        val entities = seatGradeRepository.saveAll(seatGrades.map { it.toEntity() })
        return entities.mapNotNull { it.id }
    }

    override fun findById(seatGradeId: Long): SeatGrade? {
        return seatGradeRepository.findById(seatGradeId)
            .map { it.toDomain() }
            .orElseGet { null }
    }
}