package com.example.demo.discount

import com.example.demo.member.Grade
import com.example.demo.member.Member
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
@Qualifier("fixDiscountPolicy")
class FixDiscountPolicy: DiscountPolicy {

    private val discountFixAmount = 1000

    override fun discount(member: Member, itemPrice: Int): Int {
        return when(member.grade) {
            Grade.VIP -> discountFixAmount
            else -> 0
        }
    }
}