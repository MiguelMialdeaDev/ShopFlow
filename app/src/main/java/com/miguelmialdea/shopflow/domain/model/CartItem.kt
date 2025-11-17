package com.miguelmialdea.shopflow.domain.model

data class CartItem(
    val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int
) {
    val subtotal: Double
        get() = price * quantity
}
