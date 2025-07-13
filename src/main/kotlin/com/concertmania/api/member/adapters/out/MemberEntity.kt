package com.concertmania.api.member.adapters.out

import com.concertmania.api.member.domain.RoleType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class MemberEntity private constructor(
    name: String,
    email: String,
    password: String,

    @Enumerated(EnumType.STRING)
    val roleType: RoleType,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
    var name: String = name
        protected set
    @Column(unique = true)
    var email: String = email
        protected set
    var password: String = password
        protected set

    companion object {
        fun create(name: String, email: String, password: String, roleType: RoleType): MemberEntity {
            return MemberEntity(name, email, password, roleType)
        }
    }
}