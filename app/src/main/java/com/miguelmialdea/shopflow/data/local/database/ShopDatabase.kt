package com.miguelmialdea.shopflow.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miguelmialdea.shopflow.data.local.dao.CartDao
import com.miguelmialdea.shopflow.data.local.entity.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
