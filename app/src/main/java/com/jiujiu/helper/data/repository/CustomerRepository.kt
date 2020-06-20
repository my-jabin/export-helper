package com.jiujiu.helper.data.repository

//
//import com.jiujiu.helper.data.local.dao.CustomerDao
//import com.jiujiu.helper.data.model.Customer
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.util.AppConstant
import com.jiujiu.helper.util.FirebaseLiveData
import com.jiujiu.helper.util.FirebaseQueryLiveData
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

//
@Singleton
class CustomerRepository @Inject constructor(@Named("customerCollection") private val customerCollection: CollectionReference) : AnkoLogger {

    private val uid: String? = FirebaseAuth.getInstance().currentUser?.uid


    //    fun insertOrUpdate(vararg customers: Customer?) {
//        customerDao.insertOrUpdate(*customers)
//    }
//
    fun loadAllCustomer(): LiveData<Result<List<Customer>>> {
        val query = customerCollection.whereEqualTo(AppConstant.USERUID, uid)
        return FirebaseQueryLiveData(query, Customer::class.java)
    }

    fun loadCustomerById(id: String): LiveData<Result<List<Customer>>> {
        val source = customerCollection.document(id)
        return FirebaseLiveData.Builder(Customer::class.java).source(source).build()
//        val result: MutableLiveData<Result<Customer>> = MutableLiveData()
//        customerCollection.document(id).get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                result.value = Result.success(task.result!!.toObject(Customer::class.java)!!)
//            } else {
//                result.value = Result.failure(task.exception!!)
//            }
//        }
//        return result
    }

//    fun delete(customer: Customer?) {
//        customerDao.delete(customer)
//    }
//
//    fun getCount(): Int {
//        return customerDao.count()
//    }
//
}
