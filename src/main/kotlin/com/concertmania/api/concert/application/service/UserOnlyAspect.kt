package com.concertmania.api.concert.application.service

import com.concertmania.api.member.domain.RoleType
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class UserOnlyAspect {

    @Before("@annotation(userOnly)")
    fun checkAdminAccess(joinPoint: JoinPoint, userOnly: UserOnly) {
        val signature = joinPoint.signature as MethodSignature
        val paramNames = signature.parameterNames
        val args = joinPoint.args

        val roleTypeIndex = paramNames.indexOf(userOnly.roleType)
        if (roleTypeIndex == -1) {
            throw IllegalArgumentException("UserOnly: 지정한 파라미터 '${userOnly.roleType}'를 찾을 수 없습니다.")
        }

        val roleType = args[roleTypeIndex] as? RoleType
            ?: throw IllegalArgumentException("UserOnly: '${userOnly.roleType}' 파라미터는 RoleType이어야 합니다.")

        if (roleType != RoleType.ROLE_USER) {
            throw IllegalAccessException("UserOnly: 이 기능은 관리자만 사용할 수 있습니다. 현재 역할: $roleType")
        }
    }
}