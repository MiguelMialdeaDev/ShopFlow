package com.miguelmialdea.shopflow.data.repository

import com.miguelmialdea.shopflow.data.remote.api.ShopApiService
import com.miguelmialdea.shopflow.data.remote.dto.toProduct
import com.miguelmialdea.shopflow.domain.model.Product
import com.miguelmialdea.shopflow.domain.repository.ProductRepository
import com.miguelmialdea.shopflow.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ShopApiService
) : ProductRepository {

    override suspend fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val products = api.getProducts().map { it.toProduct() }
            emit(Resource.Success(products))
        } catch (e: HttpException) {
            emit(Resource.Error("Network error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }

    override suspend fun getProductById(id: Int): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            val product = api.getProductById(id).toProduct()
            emit(Resource.Success(product))
        } catch (e: HttpException) {
            emit(Resource.Error("Network error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }

    override suspend fun getCategories(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())
        try {
            val categories = api.getCategories()
            emit(Resource.Success(categories))
        } catch (e: HttpException) {
            emit(Resource.Error("Network error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }

    override suspend fun getProductsByCategory(category: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val products = api.getProductsByCategory(category).map { it.toProduct() }
            emit(Resource.Success(products))
        } catch (e: HttpException) {
            emit(Resource.Error("Network error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}
