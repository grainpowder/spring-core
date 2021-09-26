package com.example.demo

import com.example.demo.member.Grade
import com.example.demo.member.Member
import com.example.demo.member.MemberService
import com.example.demo.order.OrderService
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {

    val ac: ApplicationContext = AnnotationConfigApplicationContext(DemoApplication::class.java)
    val memberService: MemberService = ac.getBean(MemberService::class.java)
    val orderService: OrderService = ac.getBean(OrderService::class.java)

    val memberId = 1L
    val member = Member(memberId, "memberA", Grade.VIP)
    memberService.join(member)

    val order = orderService.createOrder(memberId, "itemA", 12000)
    println("$order")
}