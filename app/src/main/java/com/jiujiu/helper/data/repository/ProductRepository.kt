package com.jiujiu.helper.data.repository

import androidx.lifecycle.LiveData
import com.jiujiu.helper.data.local.dao.ProductDao
import com.jiujiu.helper.data.model.Product
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productDao: ProductDao) : AnkoLogger {

    fun insertOrUpdate(vararg productList: Product?) {
        productDao.insertOrUpdate(*productList)
    }

    fun loadAllProducts(): LiveData<List<Product>> {
        return productDao.loadAllProducts()
    }

    fun loadProductById(id: Long?): LiveData<Product> {
        return productDao.getProductById(id!!)
    }

    fun getCount(): Int {
        return productDao.count()
    }

}
