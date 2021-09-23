package com.example.demo

import com.example.demo.discount.DiscountPolicy
import com.example.demo.discount.RateDiscountPolicy
import com.example.demo.member.MemberRepository
import com.example.demo.member.MemberService
import com.example.demo.member.MemberServiceImpl
import com.example.demo.member.MemoryMemberRepository
import com.example.demo.order.OrderService
import com.example.demo.order.OrderServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun memberRepository(): MemberRepository { return MemoryMemberRepository() }

    @Bean
    fun memberService(): MemberService { return MemberServiceImpl(memberRepository()) }

    @Bean
    fun discountPolicy(): DiscountPolicy { return RateDiscountPolicy() }

    @Bean
    fun orderService(): OrderService { return OrderServiceImpl(memberService(), discountPolicy()) }
}