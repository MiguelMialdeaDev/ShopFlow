package com.miguelmialdea.shopflow.data.repository

import com.miguelmialdea.shopflow.data.local.dao.CartDao
import com.miguelmialdea.shopflow.data.local.entity.toCartEntity
import com.miguelmialdea.shopflow.data.local.entity.toCartItem
import com.miguelmialdea.shopflow.domain.model.CartItem
import com.miguelmialdea.shopflow.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().map { entities ->
            entities.map { it.toCartItem() }
        }
    }

    override suspend fun addToCart(item: CartItem) {
        val existingItem = cartDao.getCartItemById(item.productId)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + item.quantity)
            cartDao.updateCartItem(updatedItem)
        } else {
            cartDao.insertCartItem(item.toCartEntity())
        }
    }

    override suspend fun updateCartItem(item: CartItem) {
        cartDao.updateCartItem(item.toCartEntity())
    }

    override suspend fun removeFromCart(item: CartItem) {
        cartDao.deleteCartItem(item.toCartEntity())
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override fun getCartCount(): Flow<Int> {
        return cartDao.getCartCount()
    }
}
