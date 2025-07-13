package com.concertmania.api.concert.adapters.out

import com.concertmania.api.common.annotation.PersistenceAdapter
import com.concertmania.api.concert.adapters.dto.SimpleConcertResponse
import com.concertmania.api.concert.adapters.out.ConcertMapper.toDomain
import com.concertmania.api.concert.adapters.out.ConcertMapper.toEntity
import com.concertmania.api.concert.application.port.out.ConcertPort
import com.concertmania.api.concert.domain.model.Concert
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

@PersistenceAdapter
class ConcertAdapter(
    private val entityManager: EntityManager,
    private val concertRepository: ConcertRepository
) : ConcertPort {

    override fun pageConcerts(
        keyword: String?,
        pageable: Pageable
    ): Page<SimpleConcertResponse> {
        val nativeSql = buildSql(keyword)
        val countSql = buildCountSql(keyword)

        val query = entityManager.createNativeQuery(nativeSql)
            .apply {
                if (!keyword.isNullOrBlank()) {
                    setParameter("keyword", keyword)
                }
                setFirstResult(pageable.offset.toInt())
                setMaxResults(pageable.pageSize)
            }

        val count = entityManager.createNativeQuery(countSql)
            .apply {
                if (!keyword.isNullOrBlank()) {
                    setParameter("keyword", keyword)
                }
            }
            .singleResult
            .let { (it as Number).toLong() }

        val resultList = query.resultList.map {
            val row = it as Array<*>
            SimpleConcertResponse(
                id = (row[0] as Number).toLong(),
                title = row[1] as String,
                place = row[2] as String,
            )
        }

        return PageableExecutionUtils.getPage(resultList, pageable) { count }
    }

    private fun buildSql(keyword: String?): String {
        return if (!keyword.isNullOrBlank()) {
            """
            SELECT id, title, place
            FROM concert
            WHERE search_vector @@ plainto_tsquery('simple', :keyword)
            ORDER BY id DESC
            """.trimIndent()
        } else {
            """
            SELECT id, title, place
            FROM concert
            ORDER BY id DESC
            """.trimIndent()
        }
    }

    private fun buildCountSql(keyword: String?): String {
        return if (!keyword.isNullOrBlank()) {
            """
            SELECT COUNT(*)
            FROM concert
            WHERE search_vector @@ plainto_tsquery('simple', :keyword)
            """.trimIndent()
        } else {
            """
            SELECT COUNT(*) FROM concert
            """.trimIndent()
        }
    }

    override fun findById(concertId: Long): Concert? {
        return concertRepository.findById(concertId)
            .orElseGet { null }
            .toDomain()
    }

    override fun update(concert: Concert) {
        val referenceById = concertRepository.getReferenceById(concert.id)
        referenceById.update(
            title = concert.title,
            place = concert.place,
            date = concert.dateTime,
            reservationOpenAt = concert.reservationOpenAt,
            reservationCloseAt = concert.reservationCloseAt
        )
    }

    override fun save(concert: Concert): Long {
        val save = concertRepository.save(concert.toEntity())
        return save.id ?: throw IllegalStateException("Concert ID cannot be null after save")
    }
}