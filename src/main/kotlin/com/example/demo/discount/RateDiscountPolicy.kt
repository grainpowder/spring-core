package com.example.demo.discount

import com.example.demo.member.Grade
import com.example.demo.member.Member

class RateDiscountPolicy: DiscountPolicy {

    private val discountFixRate = 10

    override fun discount(member: Member, itemPrice: Int): Int {
        return when(member.grade) {
            Grade.VIP -> itemPrice * discountFixRate / 100
            else -> 0
        }
    }
}