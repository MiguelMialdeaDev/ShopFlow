package com.miguelmialdea.shopflow.domain.repository

import com.miguelmialdea.shopflow.domain.model.Product
import com.miguelmialdea.shopflow.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Resource<List<Product>>>
    suspend fun getProductById(id: Int): Flow<Resource<Product>>
    suspend fun getCategories(): Flow<Resource<List<String>>>
    suspend fun getProductsByCategory(category: String): Flow<Resource<List<Product>>>
}
