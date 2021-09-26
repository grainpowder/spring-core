package com.example.demo.order

import com.example.demo.discount.DiscountPolicy
import com.example.demo.member.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class OrderServiceImpl(
    @Autowired private val memberServce: MemberService,
    @Qualifier("fixDiscountPolicy") @Autowired private val discountPolicy: DiscountPolicy
): OrderService {

    override fun createOrder(memberId: Long, itemName: String, itemPrice: Int): Order {
        val member = memberServce.findMember(memberId)
        val discount = discountPolicy.discount(member, itemPrice)
        return Order(memberId, itemName, itemPrice, discount)
    }
}