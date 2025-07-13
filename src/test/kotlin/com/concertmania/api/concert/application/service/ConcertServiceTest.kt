package com.concertmania.api.concert.application.service

import com.concertmania.api.concert.adapters.out.ConcertMapper.toDomain
import com.concertmania.api.concert.adapters.out.ConcertRepository
import com.concertmania.api.member.domain.RoleType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.test.Test

@Transactional
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConcertServiceTest {

    @Autowired
    private lateinit var concertService: ConcertService

    @Autowired
    private lateinit var concertRepository: ConcertRepository

    @Test
    fun `create concert 권한 검증`() {
        // given
        val roleType = RoleType.ROLE_USER // 일반 사용자로 설정

        // when & then
        assertThrows<IllegalAccessException> {
            concertService.createConcert(roleType, "Test Concert", "Test Place", LocalDate.now())
        }
    }

    @Test
    fun createConcert() {
        val roleType = RoleType.ROLE_ADMIN // 관리자 권한으로 설정
        concertService.createConcert(roleType, "Test Concert", "Test Place", LocalDate.now())
        val findAll = concertRepository.findAll()
        assertThat(findAll).hasSize(1)
        println("findAll = ${findAll.map { it.toDomain() }}")
    }
}