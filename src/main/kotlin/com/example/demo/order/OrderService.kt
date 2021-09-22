package com.example.demo.order

interface OrderService {

    fun createOrder(memberId: Long, itemName: String, itemPrice: Int): Order
}