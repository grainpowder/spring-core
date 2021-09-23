package com.example.demo

import com.example.demo.discount.DiscountPolicy
import com.example.demo.discount.RateDiscountPolicy
import com.example.demo.member.MemberRepository
import com.example.demo.member.MemberService
import com.example.demo.member.MemberServiceImpl
import com.example.demo.member.MemoryMemberRepository
import com.example.demo.order.OrderService
import com.example.demo.order.OrderServiceImpl

class AppConfig {

    fun memberRepository(): MemberRepository { return MemoryMemberRepository() }

    fun memberService(): MemberService { return MemberServiceImpl(memberRepository()) }

    fun discountPolicy(): DiscountPolicy { return RateDiscountPolicy() }

    fun orderService(memberService: MemberService): OrderService {
        return OrderServiceImpl(memberService, discountPolicy())
    }
}