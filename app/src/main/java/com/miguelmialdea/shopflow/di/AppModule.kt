package com.miguelmialdea.shopflow.di

import android.app.Application
import androidx.room.Room
import com.miguelmialdea.shopflow.data.local.database.ShopDatabase
import com.miguelmialdea.shopflow.data.remote.api.ShopApiService
import com.miguelmialdea.shopflow.data.repository.CartRepositoryImpl
import com.miguelmialdea.shopflow.data.repository.ProductRepositoryImpl
import com.miguelmialdea.shopflow.domain.repository.CartRepository
import com.miguelmialdea.shopflow.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideShopApiService(retrofit: Retrofit): ShopApiService {
        return retrofit.create(ShopApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideShopDatabase(app: Application): ShopDatabase {
        return Room.databaseBuilder(
            app,
            ShopDatabase::class.java,
            "shop_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: ShopApiService): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCartRepository(db: ShopDatabase): CartRepository {
        return CartRepositoryImpl(db.cartDao())
    }
}
