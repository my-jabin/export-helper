package com.jiujiu.helper.ui.customers.addEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.ui.base.BaseViewModel
import com.jiujiu.helper.util.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditCustomerViewModel @Inject constructor(
        dataManager: DataManager
) : BaseViewModel(dataManager) {

    private val _customerLiveData: MediatorLiveData<Result<List<Customer>>> = MediatorLiveData()
    val customerLiveData: LiveData<Result<List<Customer>>>
        get() = _customerLiveData

    private val _navigateUpEvent = MutableLiveData<Event<Unit>>()
    val navigateUpEvent: LiveData<Event<Unit>>
        get() = _navigateUpEvent

    fun loadCustomerById(id: String) {
        viewModelScope.launch {
            _customerLiveData.addSource(dataManager.getCustomerById(id)) { _customerLiveData.postValue(it) }
        }
    }

    fun saveCustomer(customer: Customer?) {
        viewModelScope.launch {
//            if (customer?.street == null) {
//                customer?.address = null
//            }
//            dataManager.saveCustomer(customer)
        }
        navigateUp()
    }

    private fun navigateUp() {
        _navigateUpEvent.value = Event(Unit)
    }

}