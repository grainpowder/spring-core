package com.example.demo.order

import com.example.demo.discount.DiscountPolicy
import com.example.demo.discount.FixDiscountPolicy
import com.example.demo.discount.RateDiscountPolicy
import com.example.demo.member.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class OrderServiceImplTest {

    private lateinit var memberService: MemberService

    @BeforeEach
    fun initializeMemberService() {
        memberService = MemberServiceImpl(MemoryMemberRepository())
    }

    @Test
    @DisplayName("VIP 고객에게 정률 할인을 적용하면 이 고객이 생성한 주문에는 10% 할인된 금액이 계산되어 있다")
    fun vipRateOrder() {
        //given
        val memberId = 1L
        val member = Member(memberId, "memberA", Grade.VIP)
        //when
        memberService.join(member)
        val orderService = OrderServiceImpl(memberService, RateDiscountPolicy())
        val order = orderService.createOrder(memberId, "itemA", 12000)
        //then
        assertEquals(1200, order.discount)
    }

    @Test
    @DisplayName("NORMAL 고객에게 정률 할인을 적용하면 이 고객이 생성한 주문에는 할인이 적용되지 않는다")
    fun normalRateOrder() {
        //given
        val memberId = 1L
        val member = Member(memberId, "memberA", Grade.NORMAL)
        //when
        memberService.join(member)
        val orderService = OrderServiceImpl(memberService, RateDiscountPolicy())
        val order = orderService.createOrder(memberId, "itemA", 12000)
        //then
        assertEquals(0, order.discount)
    }

    @Test
    @DisplayName("VIP 고객에게 정액 할인을 적용하면 이 고객이 생성한 주문에는 할인 금액 1000원이 저장되어 있다")
    fun vipFixOrder() {
        //given
        val memberId = 1L
        val member = Member(memberId, "memberA", Grade.VIP)
        //when
        memberService.join(member)
        val orderService = OrderServiceImpl(memberService, FixDiscountPolicy())
        val order = orderService.createOrder(memberId, "itemA", 12000)
        //then
        assertEquals(1000, order.discount)
    }

    @Test
    @DisplayName("NORMAL 고객에게 정액 할인을 적용하면 이 고객이 생성한 주문에는 할인이 적용되지 않는다")
    fun normalFixOrder() {
        //given
        val memberId = 1L
        val member = Member(memberId, "memberA", Grade.NORMAL)
        //when
        memberService.join(member)
        val orderService = OrderServiceImpl(memberService, FixDiscountPolicy())
        val order = orderService.createOrder(memberId, "itemA", 12000)
        //then
        assertEquals(0, order.discount)
    }

}