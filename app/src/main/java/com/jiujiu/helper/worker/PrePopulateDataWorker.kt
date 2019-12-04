package com.jiujiu.helper.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.jiujiu.helper.R
import com.jiujiu.helper.data.local.dao.CustomerDao
import com.jiujiu.helper.data.local.dao.ProductTypeDao
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.data.model.ProductType
import com.jiujiu.helper.data.repository.ProductRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class PrePopulateDataWorker @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted workerParams: WorkerParameters,
        private val mProductRepository: ProductRepository,
        private val mProductTypeDao: ProductTypeDao,
        private val mCustomerDao: CustomerDao
) : CoroutineWorker(context, workerParams) {

    override val coroutineContext = Dispatchers.IO

    override suspend fun doWork(): Result {
        return coroutineScope {
            prePopulateProductTypes()
            prePopulateProducts()
            prePopulateCustomer()
            Result.success()
        }
    }

    private fun prePopulateCustomer() {
        if (mCustomerDao.count() < 1) {
            val reader = context.resources.assets.open("customers.json")
            val jsonReader = JsonReader(reader.reader())
            val gson = Gson()
            val collectionType = object : TypeToken<Collection<Customer>>() {}.type
            val customers = gson.fromJson<List<Customer>>(jsonReader, collectionType)
            mCustomerDao.insertOrUpdate(*customers.toTypedArray())
        }

//        val customers = arrayListOf<Customer>().apply {
//            add(Customer(
//                    name = "Yanbin Hu",
//                    address = Address(city = "沙shaSHA县", street = "凤岗镇莲花新村的北路265-123号", province = "福建省fujiansheng", postCode = 365500),
//                    IDNumber = "35042919382948203",
//                    phoneNumber = "13203939292",
//                    email = "default@email.com"
//            ))
//            add(Customer(
//                    name = "Hu Yanbin",
//                    address = Address(province = "福建省", city = "厦门市湖里区", street = "圆山南路202号"),
//                    IDNumber = "350429198803238402",
//                    phoneNumber = "13203939292",
//                    email = "default@email.com"
//            ))
//            add(Customer("Guo Yanxue", address = Address(province = "福建省", city = "厦门市湖里区", street = "圆山南路202号")))
//            add(Customer("Yanxue Guo", address = Address(province = "广东省", city = "深圳市", street = "不认识的路202号")))
//            add(Customer("胡延彬", address = Address(province = "北京市", city = "通州区", street = "山南路12号")))
//            add(Customer("郭燕雪", address = Address(province = "福建省", city = "厦门市湖里区", street = "圆山南路202号")))
//        }
//        mCustomerDao.insertOrUpdate(*customers.toTypedArray())
    }

    private fun prePopulateProductTypes() {
        if (this.mProductTypeDao.count() <= 0) {
            val types = arrayListOf<ProductType>().apply {
                add(ProductType(context.getString(R.string.text_milk_powder)))
                add(ProductType(context.getString(R.string.text_clothes)))
                add(ProductType(context.getString(R.string.text_cosmetic)))
                add(ProductType(context.getString(R.string.text_luxury)))
                add(ProductType(context.getString(R.string.text_alcohol)))
            }
            mProductTypeDao.insert(*types.toTypedArray())
        }
    }

    private fun prePopulateProducts() {
        if (this.mProductRepository.getCount() <= 0) {
            val reader = context.resources.assets.open("data.json")
            val jsonReader = JsonReader(reader.reader())
            val gson = Gson()
            val collectionType = object : TypeToken<Collection<Product>>() {}.type
            val products = gson.fromJson<List<Product>>(jsonReader, collectionType)
            mProductRepository.insertOrUpdate(*products.toTypedArray())
        }
    }

    @AssistedInject.Factory
    interface Factory : CustomWorkerFactory
}
