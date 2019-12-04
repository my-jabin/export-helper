package com.jiujiu.helper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jiujiu.helper.data.local.dao.CustomerDao
import com.jiujiu.helper.data.local.dao.ProductDao
import com.jiujiu.helper.data.local.dao.ProductTypeDao
import com.jiujiu.helper.data.model.Converters
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.data.model.ProductType

@Database(entities = arrayOf(Product::class, ProductType::class, Customer::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productTypeDao(): ProductTypeDao
    abstract fun customerDao(): CustomerDao
//    abstract fun orderDao(): OrderDao
}
