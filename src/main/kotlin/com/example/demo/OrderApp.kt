package com.example.demo

import com.example.demo.member.Grade
import com.example.demo.member.Member
import com.example.demo.member.MemberService
import com.example.demo.order.OrderService

fun main() {

    val appConfig = AppConfig()
    val memberService: MemberService = appConfig.memberService()
    val orderService: OrderService = appConfig.orderService(memberService)

    val memberId = 1L
    val member = Member(memberId, "memberA", Grade.VIP)
    memberService.join(member)

    val order = orderService.createOrder(memberId, "itemA", 12000)
    println("$order")
}