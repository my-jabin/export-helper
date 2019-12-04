package com.jiujiu.helper

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jiujiu.helper.data.local.dao.ProductDao
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest : BaseDaoTest() {
    lateinit var productDao: ProductDao


    override fun generateDaoInstance() {
        productDao = db.productDao()
    }

    @Test
    fun insert() {
    }
}