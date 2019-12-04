package com.jiujiu.helper.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.jiujiu.helper.data.local.dao.ProductTypeDao
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.data.model.ProductType
import com.jiujiu.helper.data.repository.CurrencyRepository
import com.jiujiu.helper.data.repository.CustomerRepository
import com.jiujiu.helper.data.repository.ProductRepository
import com.jiujiu.helper.util.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
        private val context: Context,
        private val mPreference: AppPreference,
        private val mProductRepository: ProductRepository,
        private val mCustomerRepository: CustomerRepository,
        private val mProductTypeDao: ProductTypeDao,
        private val mCurrencies: CurrencyRepository
) {

    fun getCurrency(locale: Locale) = this.mCurrencies.getCurrency(locale)

    fun getCurrencies() = this.mCurrencies.getcurrencies()

    var currentUserName: String?
        get() = this.mPreference.userName
        set(userName) {
            this.mPreference.userName = userName
        }

    val allProducts: LiveData<List<Product>>
        get() = this.mProductRepository.loadAllProducts()

    val allCustomers: LiveData<List<Customer>>
        get() = this.mCustomerRepository.loadAllCustomer()

    init {
        currentUserName = AppConstant.USERNAME
    }

    fun getProductById(id: Long?): LiveData<Product> {
        return this.mProductRepository.loadProductById(id)
    }

    fun getAllProductType(): LiveData<List<ProductType>> {
        return this.mProductTypeDao.loadAllTypes()
    }

    fun getTypeById(id: Long?): ProductType {
        return mProductTypeDao.getTypeById(id)
    }


    fun getCustomerById(id: Long?): LiveData<Customer> {
        return this.mCustomerRepository.loadCustomerById(id)
    }


    suspend fun saveProduct(product: Product?) {
        withContext(Dispatchers.IO) {
            mProductRepository.insertOrUpdate(product)
        }
    }

    suspend fun saveCustomer(customer: Customer?) {
        withContext(Dispatchers.IO) {
            mCustomerRepository.insertOrUpdate(customer)
        }
    }

    suspend fun deleteCustomer(customer: Customer?) {
        withContext(Dispatchers.IO) {
            mCustomerRepository.delete(customer)
        }
    }


}
