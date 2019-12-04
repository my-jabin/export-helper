package com.jiujiu.helper.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jiujiu.helper.data.model.Customer

@Dao
interface CustomerDao {

    @Query("select * from customer where id = :id")
    fun getCustomerById(id: Long): LiveData<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg customers: Customer?)

    @Delete
    fun delete(product: Customer?)

    @Query("SELECT * FROM customer")
    fun loadAllCustomers(): LiveData<List<Customer>>

    @Query("select count(1) from customer")
    fun count(): Int
}
