package com.concertmania.api.concert.application.service

import com.concertmania.api.member.domain.RoleType
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class AdminOnlyAspect {

    @Before("@annotation(adminOnly)")
    fun checkAdminAccess(joinPoint: JoinPoint, adminOnly: AdminOnly) {
        val signature = joinPoint.signature as MethodSignature
        val paramNames = signature.parameterNames
        val args = joinPoint.args

        val roleTypeIndex = paramNames.indexOf(adminOnly.roleType)
        if (roleTypeIndex == -1) {
            throw IllegalArgumentException("AdminOnly: 지정한 파라미터 '${adminOnly.roleType}'를 찾을 수 없습니다.")
        }

        val roleType = args[roleTypeIndex] as? RoleType
            ?: throw IllegalArgumentException("AdminOnly: '${adminOnly.roleType}' 파라미터는 RoleType이어야 합니다.")

        if (roleType != RoleType.ROLE_ADMIN) {
            throw IllegalAccessException("AdminOnly: 이 기능은 관리자만 사용할 수 있습니다. 현재 역할: $roleType")
        }
    }
}