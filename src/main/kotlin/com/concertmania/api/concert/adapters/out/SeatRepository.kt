package com.concertmania.api.concert.adapters.out

import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository: JpaRepository<SeatEntity, Long> {

}
