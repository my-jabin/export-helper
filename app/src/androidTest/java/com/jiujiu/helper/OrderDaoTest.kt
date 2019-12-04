package com.jiujiu.helper


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jiujiu.helper.data.local.dao.OrderDao
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderDaoTest : BaseDaoTest() {
    //todo: add unit test?
    private lateinit var orderDao: OrderDao

    override fun generateDaoInstance() {
//        orderDao = db.orderDao()
    }

    @Test
    @Throws(Exception::class)
    fun insertOrder() {
    }


}