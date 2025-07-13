package com.concertmania.api.auth.application.service

import com.concertmania.api.member.application.service.MemberService
import com.concertmania.api.member.domain.Member
import com.concertmania.api.member.domain.RoleType
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("local")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthServiceTest {

    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var memberService: MemberService

    @Test
    fun test() {
        memberService.createMember(
            name = "test",
            email = "test@test.com",
            password = "test",
            roleType = RoleType.ROLE_USER
        )
        val signIn = authService.signIn("test@test.com", "test")
        assertNotNull(signIn)
        assertNotNull(signIn.accessToken)
        println("Access Token: ${signIn.accessToken}")
    }
}