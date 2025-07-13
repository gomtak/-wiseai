package com.concertmania.api.concert.application.port.out

import com.concertmania.api.concert.adapters.dto.SimpleConcertResponse
import com.concertmania.api.concert.domain.model.Concert
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ConcertPort {
    fun pageConcerts(keyword: String?, pageable: Pageable): Page<SimpleConcertResponse>
    fun findById(concertId: Long): Concert?
    fun update(concert: Concert)
    fun save(concert: Concert): Long

}
