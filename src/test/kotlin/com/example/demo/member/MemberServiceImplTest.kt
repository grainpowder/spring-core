package com.example.demo.member

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class MemberServiceImplTest {

    private val memberService = MemberServiceImpl(MemoryMemberRepository())

    @Test
    @DisplayName("가입한 회원을 조회할 수 있다")
    fun find_joined() {
        //given
        val member = Member(1L, "memberA", Grade.NORMAL)
        //when
        memberService.join(member)
        val foundMember = memberService.findMember(1L)
        //then
        assertEquals(member, foundMember)
    }

    @Test
    @DisplayName("가입하지 않은 회원을 조회하면 에러가 발생된다")
    fun find_not_joined() {
        val errorMessage = assertThrows(AssertionError::class.java) {
            memberService.findMember(100)
        }
        assertEquals(errorMessage.message, "Member with id=100 not found")
    }
}