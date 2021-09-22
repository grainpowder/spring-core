package com.example.demo.discount

import com.example.demo.member.Member

interface DiscountPolicy {

    fun discount(member: Member, itemPrice: Int): Int
}