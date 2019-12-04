package com.jiujiu.helper.ui.products.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.data.model.ProductType
import com.jiujiu.helper.ui.base.BaseViewModel
import com.jiujiu.helper.util.Event
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ProductAddViewModel @Inject constructor(
        dataManager: DataManager
) : BaseViewModel(dataManager) {

    private val mProductTypeLiveData: MediatorLiveData<List<ProductType>> = MediatorLiveData()

    val productTypeLiveData: LiveData<List<ProductType>>
        get() = mProductTypeLiveData

    val hasImage: LiveData<Boolean> = MutableLiveData(false)
    val product: MutableLiveData<Product> = MutableLiveData(Product())
    val currencies = this.dataManager.getCurrencies().toList()

    private val _navigateUpEvent = MutableLiveData<Event<Unit>>()
    val navigateUpEvent: LiveData<Event<Unit>>
        get() = _navigateUpEvent

    fun getCurrency(locale: Locale) = this.dataManager.getCurrency(locale)

    fun saveProduct() {
        // go back to product list fragment
        viewModelScope.launch {
            dataManager.saveProduct(product.value)
        }
        navigateUp()
    }

    private fun navigateUp() {
        _navigateUpEvent.value = Event(Unit)
    }

    fun loadAllProductType() {
        mProductTypeLiveData.addSource(dataManager.getAllProductType()) { mProductTypeLiveData.value = it }
    }
}
