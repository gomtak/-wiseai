package com.concertmania.api.concert.adapters.out

import org.springframework.data.jpa.repository.JpaRepository

interface SeatGradeRepository : JpaRepository<SeatGradeEntity, Long> {
}
