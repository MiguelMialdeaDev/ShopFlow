package com.miguelmialdea.shopflow.domain.repository

import com.miguelmialdea.shopflow.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(item: CartItem)
    suspend fun updateCartItem(item: CartItem)
    suspend fun removeFromCart(item: CartItem)
    suspend fun clearCart()
    fun getCartCount(): Flow<Int>
}
