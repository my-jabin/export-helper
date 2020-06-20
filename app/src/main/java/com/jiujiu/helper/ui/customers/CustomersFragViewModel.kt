package com.jiujiu.helper.ui.customers

import androidx.lifecycle.LiveData
import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.ui.base.BaseViewModel
import org.jetbrains.anko.info
import javax.inject.Inject

class CustomersFragViewModel @Inject constructor(
        dataManager: DataManager
) : BaseViewModel(dataManager) {

    fun deleteCustomer(customer: Customer) {
//        viewModelScope.launch {
//            dataManager.deleteCustomer(customer)
//        }
    }

    val customerLiveData: LiveData<Result<List<Customer>>> by lazy {
        info { "lazy init" }
        dataManager.allCustomers
    }

}
