package com.example.demo.member

import org.springframework.stereotype.Component

@Component
class MemoryMemberRepository: MemberRepository {

    private val store: MutableMap<Long, Member> = mutableMapOf()

    override fun save(member: Member) {
        store[member.id] = member
    }

    override fun findById(memberId: Long): Member {
        assert(store.containsKey(memberId)) { "Member with id=$memberId not found" }
        return store[memberId]!!
    }
}