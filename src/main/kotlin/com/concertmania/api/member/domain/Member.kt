package com.concertmania.api.member.domain

import com.concertmania.api.common.exception.EntityNotPersistedException

class Member(
    private val _id: Long? = null,
    name: String,
    email: String,
    password: String,
    val roleType: RoleType
) {
    val id: Long
        get() = _id ?: throw EntityNotPersistedException(this::class.simpleName)
    var name: String = name
        protected set
    var email: String = email
        protected set
    var password: String = password
        protected set

    companion object {
        fun create(name: String, email: String, password: String, roleType: RoleType): Member {
            return Member(name = name, email = email, password = password, roleType = roleType)
        }

        fun of(
            id: Long,
            name: String,
            email: String,
            password: String,
            roleType: RoleType
        ): Member {
            return Member(_id = id, name = name, email = email, password = password, roleType = roleType)
        }
    }
}