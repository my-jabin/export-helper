package com.jiujiu.helper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.jiujiu.helper.data.local.dao.ProductDao
import com.jiujiu.helper.data.model.Converters
import com.jiujiu.helper.data.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
