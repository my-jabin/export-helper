package com.jiujiu.helper.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jiujiu.helper.data.model.Product

@Dao
interface ProductDao {

    @Query("select * from Product where id = :id")
    fun getProductById(id: Long): LiveData<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg products: Product?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product?)

    @Delete
    fun delete(product: Product)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(product: Product)

    @Query("SELECT * FROM product")
    fun loadAllProducts(): LiveData<List<Product>>

    @Query("select count(1) from Product")
    fun count(): Int
}
