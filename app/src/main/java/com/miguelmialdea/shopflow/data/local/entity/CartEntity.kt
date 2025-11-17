package com.miguelmialdea.shopflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miguelmialdea.shopflow.domain.model.CartItem

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int
)

fun CartEntity.toCartItem(): CartItem {
    return CartItem(
        productId = productId,
        title = title,
        price = price,
        image = image,
        quantity = quantity
    )
}

fun CartItem.toCartEntity(): CartEntity {
    return CartEntity(
        productId = productId,
        title = title,
        price = price,
        image = image,
        quantity = quantity
    )
}
