package com.miguelmialdea.shopflow.data.remote.dto

import com.miguelmialdea.shopflow.domain.model.Product
import com.miguelmialdea.shopflow.domain.model.Rating

data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDto
)

data class RatingDto(
    val rate: Double,
    val count: Int
)

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = Rating(
            rate = rating.rate,
            count = rating.count
        )
    )
}
