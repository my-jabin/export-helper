package com.jiujiu.helper.data.repository

import androidx.lifecycle.LiveData
import com.jiujiu.helper.data.local.dao.CustomerDao
import com.jiujiu.helper.data.model.Customer
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepository @Inject constructor(private val customerDao: CustomerDao) : AnkoLogger {

    fun insertOrUpdate(vararg customers: Customer?) {
        customerDao.insertOrUpdate(*customers)
    }

    fun loadAllCustomer(): LiveData<List<Customer>> {
        return customerDao.loadAllCustomers()
    }

    fun loadCustomerById(id: Long?): LiveData<Customer> {
        return customerDao.getCustomerById(id!!)
    }

    fun delete(customer: Customer?) {
        customerDao.delete(customer)
    }

    fun getCount(): Int {
        return customerDao.count()
    }

}
