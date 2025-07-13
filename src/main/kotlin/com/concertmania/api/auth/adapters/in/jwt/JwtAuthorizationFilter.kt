package com.concertmania.api.auth.adapters.`in`.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

private const val AUTHORIZATION_HEADER: String = "Authorization"
private const val BEARER_PREFIX = "Bearer "

class JwtAuthorizationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        try {
            val token = resolveToken(request)

            if (!token.isNullOrBlank() && StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
                val userName = jwtProvider.getUserName(token)
                val roleType = jwtProvider.getRoleType(token)

                SecurityContextHolder.getContext().authentication = JwtAuthenticationToken(userName, roleType)
            }
        } catch (e: Exception) {
            logger.error("❌ JWT 처리 중 오류 발생: ${e.message}", e)
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.substring(BEARER_PREFIX.length)
        } else null
    }
}