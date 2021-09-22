package com.example.demo.order

import com.example.demo.discount.DiscountPolicy
import com.example.demo.member.MemberService

class OrderServiceImpl(
    private val memberServce: MemberService,
    private val discountPolicy: DiscountPolicy
): OrderService {

    override fun createOrder(memberId: Long, itemName: String, itemPrice: Int): Order {
        val member = memberServce.findMember(memberId)
        val discount = discountPolicy.discount(member, itemPrice)
        return Order(memberId, itemName, itemPrice, discount)
    }
}