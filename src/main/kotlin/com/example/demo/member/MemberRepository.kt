package com.example.demo.member

interface MemberRepository {

    fun save(member: Member)

    fun findById(memberId: Long): Member
}