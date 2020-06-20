package com.jiujiu.helper.data

import android.content.Context
import android.icu.util.Currency
import androidx.lifecycle.LiveData
import com.jiujiu.helper.R
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.data.repository.CurrencyRepository
import com.jiujiu.helper.data.repository.CustomerRepository
import com.jiujiu.helper.data.repository.ProductRepository
import com.jiujiu.helper.data.repository.ProductTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.configuration
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
        private val context: Context,
        private val mProductRepository: ProductRepository,
        private val mCustomerRepository: CustomerRepository,
        private val mProductTypeRepository: ProductTypeRepository,
//        private val mProductTypeDao: ProductTypeDao,
        private val mCurrencies: CurrencyRepository
) {

    fun getCurrency(locale: Locale) = this.mCurrencies.getCurrency(locale)

    fun getLocalCurrency(): Currency = Currency.getInstance(context.configuration.locales[0])

    fun getCurrencies() = this.mCurrencies.getCurrencies()

//    var currentUserName: String?
//        get() = this.mPreference.userName
//        set(userName) {
//            this.mPreference.userName = userName
//        }

    val allProducts: LiveData<Result<List<Product>>>
        get() = this.mProductRepository.loadAllProducts()

    val allCustomers: LiveData<Result<List<Customer>>>
        get() = this.mCustomerRepository.loadAllCustomer()

//    init {
//        currentUserName = AppConstant.USERNAME
//    }

    fun getProductById(id: String): LiveData<Result<Product?>> {
        return this.mProductRepository.loadProductById(id)
    }

    fun deleteProductById(id: String) {
        return this.mProductRepository.deleteById(id)
    }

//    fun getAllProductType(): LiveData<Result<List<ProductType>>> {
//        return this.mProductTypeRepository.loadAllTypes()
//    }

    fun getAllProductType(): List<String> {
        // todo: get product types from FireStore?
        return context.resources.getStringArray(R.array.product_types).toList()
    }
//
//    fun getTypeById(id: Long?): ProductType {
//        return mProductTypeDao.getTypeById(id)
//    }


    // todo: looks strange when the liveData return a list of customer, should return just one customer
    fun getCustomerById(id: String): LiveData<Result<List<Customer>>> {
        return this.mCustomerRepository.loadCustomerById(id)
    }

    suspend fun saveProduct(product: Product) {
        withContext(Dispatchers.IO) {
            mProductRepository.add(product)
        }
    }
//
//    suspend fun saveCustomer(customer: Customer?) {
//        withContext(Dispatchers.IO) {
//            mCustomerRepository.insertOrUpdate(customer)
//        }
//    }
//
//    suspend fun deleteCustomer(customer: Customer?) {
//        withContext(Dispatchers.IO) {
//            mCustomerRepository.delete(customer)
//        }
//    }


}
