package com.miguelmialdea.shopflow.data.remote.api

import com.miguelmialdea.shopflow.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopApiService {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<ProductDto>
}
