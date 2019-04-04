package com.jiujiu.helper.data

import androidx.lifecycle.LiveData
import com.jiujiu.helper.data.local.dao.ProductTypeDao
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.data.model.ProductType
import com.jiujiu.helper.data.repository.ProductRepository
import com.jiujiu.helper.util.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
        private val mPreference: AppPreference,
        private val mProductRepository: ProductRepository,
        private val mProductTypeDao: ProductTypeDao
) {

    var currentUserName: String?
        get() = this.mPreference.userName
        set(userName) {
            this.mPreference.userName = userName
        }

    val allProducts: LiveData<List<Product>>
        get() = this.mProductRepository.loadAllProducts()

    init {
        currentUserName = AppConstant.USERNAME
    }

    suspend fun prePopulateData() {
        withContext(Dispatchers.IO) {

            val types = arrayListOf<ProductType>().apply {
                for(i in 1..9){
                    add(ProductType("ProductType $i"))
                }
            }
            mProductTypeDao.insert(*types.toTypedArray())

            val products = arrayListOf<Product>().apply {
                for (i in 1..5) {
                    add(Product("product name $i", "Brand $i").apply {
                        salePrice = i * 10.0
                        model = "Model $i"
                        typeId = i
                    })
                }
            }
            mProductRepository.insert(*products.toTypedArray())


        }
    }

    fun getProductById(id: Long?): LiveData<Product> {
        return this.mProductRepository.loadProductById(id)
    }
}
