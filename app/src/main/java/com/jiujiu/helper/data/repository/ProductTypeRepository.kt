package com.jiujiu.helper.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.jiujiu.helper.data.model.ProductType
import com.jiujiu.helper.util.AppConstant
import com.jiujiu.helper.util.FirebaseLiveData
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ProductTypeRepository @Inject constructor(@Named("productTypeCollection") private val typeCollection: CollectionReference) : AnkoLogger {

    private val uid: String? = FirebaseAuth.getInstance().currentUser?.uid

    //    fun insertOrUpdate(vararg productList: Product?) {
//        produhttps://developer.android.com/topic/libraries/architecture/livedata#update_livedata_objectsctDao.insertOrUpdate(*productList)
//    }

    fun insert(type: ProductType?) {
        if (type != null)
            typeCollection.add(type)
    }

    fun loadAllTypes(): LiveData<Result<List<ProductType>>> {
        val query = typeCollection.whereEqualTo(AppConstant.USERUID, uid)
        return FirebaseLiveData.Builder(ProductType::class.java).source(query).build()
    }

//    fun loadProductById(id: Long?): LiveData<Result<Product>> {
//        productCollection.whereEqualTo("userUID",uid).
//        return productDao.getProductById(id!!)
//    }

//    fun getCount(): Int {
//        return productDao.count()
//    }

}
