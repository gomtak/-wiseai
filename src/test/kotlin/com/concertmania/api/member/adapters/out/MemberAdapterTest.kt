package com.concertmania.api.member.adapters.out

import com.concertmania.api.member.domain.Member
import com.concertmania.api.member.domain.RoleType
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@Transactional
@SpringBootTest
@Profile("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberAdapterTest {

    @Autowired
    private lateinit var memberAdapter: MemberAdapter

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    fun `User 저장 - UserAdapter 를 통해 영속화`() {
        // given
        val member = Member.create(
            name = "Test User",
            email = "test@test.com",
            password = "password123",
            roleType = RoleType.ROLE_USER
        )
        memberAdapter.save(member)

        // then
        val result = memberRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].email).isEqualTo("test@test.com")
        assertThat(result[0].name).isEqualTo("Test User")
        assertThat(result[0].password).isEqualTo("password123")
        assertThat(result[0].roleType).isEqualTo(RoleType.ROLE_USER)
    }
}
