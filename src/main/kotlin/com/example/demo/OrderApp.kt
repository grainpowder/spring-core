package com.example.demo

import com.example.demo.discount.DiscountPolicy
import com.example.demo.discount.RateDiscountPolicy
import com.example.demo.member.*
import com.example.demo.order.OrderService
import com.example.demo.order.OrderServiceImpl

fun main() {

    val memberRepository: MemberRepository = MemoryMemberRepository()
    val memberService: MemberService = MemberServiceImpl(memberRepository)
    val discountPolicy: DiscountPolicy = RateDiscountPolicy()
    val orderService: OrderService = OrderServiceImpl(memberService, discountPolicy)

    val memberId = 1L
    val member = Member(memberId, "memberA", Grade.VIP)
    memberService.join(member)

    val order = orderService.createOrder(memberId, "itemA", 12000)
    println("$order")
}