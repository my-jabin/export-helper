package com.jiujiu.helper.ui.customers

import androidx.lifecycle.MutableLiveData

import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.ui.base.BaseViewModel

import javax.inject.Inject

class CustomersFragViewModel @Inject constructor(
        dataManager: DataManager
) : BaseViewModel(dataManager) {

    var customerName = MutableLiveData<String>()

    init {
        customerName.value = dataManager.currentUserName
    }

}
