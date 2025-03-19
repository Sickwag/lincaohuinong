package com.example.lingcaohuinong.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int,
    val category: String
)

class CartItem(
    val product: Product,
    initialQuantity: Int = 1
) {
    var quantity by mutableStateOf(initialQuantity)
    val totalPrice: Double
        get() = product.price * quantity
} 