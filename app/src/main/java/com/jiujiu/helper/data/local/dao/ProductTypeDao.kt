package com.jiujiu.helper.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jiujiu.helper.data.model.ProductType

@Dao
interface ProductTypeDao {

    @Query("select * from producttype where id = :id")
    fun getTypeById(id: Long): LiveData<ProductType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg types: ProductType)

    @Delete
    fun delete(type: ProductType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(type: ProductType)

    @Query("SELECT * FROM producttype")
    fun loadAllTypes(): LiveData<List<ProductType>>
}