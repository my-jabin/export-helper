package com.jiujiu.helper.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.util.AppConstant
import com.jiujiu.helper.util.FirebaseLiveData
import com.jiujiu.helper.util.getFirstAsLiveData
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(@Named("productCollection") private val productCollection: CollectionReference) : AnkoLogger {

    private val uid: String? = FirebaseAuth.getInstance().currentUser?.uid

    fun add(product: Product) {
        productCollection.add(product)
    }

    fun loadAllProducts(): LiveData<Result<List<Product>>> {
        val query = productCollection.whereEqualTo(AppConstant.USERUID, uid)
        return FirebaseLiveData.Builder(Product::class.java).source(query).build()
    }

    fun loadProductById(id: String): LiveData<Result<Product?>> {
        val query = productCollection.document(id)
        val queryLiveData = FirebaseLiveData.Builder(Product::class.java).source(query).build()
        return queryLiveData.getFirstAsLiveData()
    }

    fun deleteById(id: String) {
        val deleteTask = productCollection.document(id).delete()
        deleteTask.addOnCompleteListener { task ->
            info { task.isSuccessful }
        }
    }
//    }

//    fun getCount(): Int {
//        return productDao.count()
//    }

}
