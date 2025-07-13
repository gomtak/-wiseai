package com.concertmania.api.auth.adapters.`in`.jwt

import com.concertmania.api.auth.adapters.dto.TokenResponse
import com.concertmania.api.auth.adapters.out.security.CustomUserDetails
import com.concertmania.api.member.domain.RoleType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID

private const val ROLE = "role"

@Component
class JwtProvider(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
    @Value("\${jwt.access-lifetime}")
    private val accessSecond: Long,
) {
    fun generateToken(user: CustomUserDetails): TokenResponse {
        val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
        val now = Date()
        val accessToken = Jwts.builder()
            .setSubject(user.username)
            .claim(ROLE, user.roleType)
            .setExpiration(Date(now.time + accessSecond * 1000))
            .setNotBefore(now)
            .setIssuedAt(now)
            .setId(UUID.randomUUID().toString())
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return TokenResponse(accessToken)
    }

    fun validateToken(token: String): Boolean {
        val claims = parseClaims(token)
        return !claims.expiration.before(Date())
    }

    fun getUserName(token: String): String {
        val claims = parseClaims(token)
        return claims.subject
    }

    fun getRoleType(token: String): RoleType {
        val claims = parseClaims(token)
        return RoleType.valueOf(claims[ROLE] as String)
    }

    private fun parseClaims(token: String): Claims {
        val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
