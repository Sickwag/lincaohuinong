package com.example.lingcaohuinong.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object CartManager {
    private val _items = mutableStateListOf<CartItem>()
    val items: SnapshotStateList<CartItem> = _items
    
    fun addProduct(product: Product) {
        val existingItem = _items.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            _items.add(CartItem(product))
        }
    }
    
    fun removeItem(item: CartItem) {
        _items.remove(item)
    }
    
    fun updateQuantity(item: CartItem, quantity: Int) {
        if (quantity <= 0) {
            _items.remove(item)
        } else {
            item.quantity = quantity
        }
    }
    
    fun clearCart() {
        _items.clear()
    }
    
    val totalItems: Int
        get() = _items.sumOf { it.quantity }
    
    val totalPrice: Double
        get() = _items.sumOf { it.totalPrice }
} 