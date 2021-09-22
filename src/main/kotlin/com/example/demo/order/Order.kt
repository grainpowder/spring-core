package com.example.demo.order

data class Order(
    val memberId: Long,
    val itemName: String,
    val itemPrice: Int,
    val discount: Int,
)