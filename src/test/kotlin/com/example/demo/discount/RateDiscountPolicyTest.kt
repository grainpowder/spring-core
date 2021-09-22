package com.example.demo.discount

import com.example.demo.member.Grade
import com.example.demo.member.Member
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class RateDiscountPolicyTest {

    private val discountPolicy = RateDiscountPolicy()

    @Test
    @DisplayName("VIP 회원에게는 10% 할인이 적용된다")
    fun vipTest() {
        //given
        val member = Member(1L, "memberA", Grade.VIP)
        //when
        val itemPrice = 12000
        val discountAmount = discountPolicy.discount(member, itemPrice)
        //then
        assertEquals(1200, discountAmount)
    }

    @Test
    @DisplayName("NORMAL 회원에게는 10% 할인이 적용되지 않는다")
    fun normalTest() {
        //given
        val member = Member(1L, "memberA", Grade.NORMAL)
        //when
        val itemPrice = 12000
        val discountAmount = discountPolicy.discount(member, itemPrice)
        //then
        assertEquals(0, discountAmount)
    }
}