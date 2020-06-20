package com.jiujiu.helper.ui.products.add

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jiujiu.helper.R
import com.jiujiu.helper.data.AppPreference
import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.ui.base.BaseViewModel
import com.jiujiu.helper.ui.widget.SingleSelectorDialog
import com.jiujiu.helper.util.Event
import com.jiujiu.helper.util.notifyObserver
import kotlinx.coroutines.launch
import org.jetbrains.anko.info
import javax.inject.Inject

class ProductAddViewModel @Inject constructor(
        dataManager: DataManager,
        private val appPreference: AppPreference
) : BaseViewModel(dataManager) {

//    private val mProductTypeLiveData: MediatorLiveData<Result<List<ProductType>>> = MediatorLiveData()

//    val productTypeLiveData: LiveData<Result<List<ProductType>>>
//        get() = mProductTypeLiveData

    val hasImage: LiveData<Boolean> = MutableLiveData(false)

    // add more two livedate for product name and product brand, as they are required.
    val product: MutableLiveData<Product> = MutableLiveData(createProduct())
    val productObj = Product()

    val productName: MutableLiveData<String> = MutableLiveData()
    val productBrand: MutableLiveData<String> = MutableLiveData()

    private val currencies = this.dataManager.getCurrencies().toList().sortedBy { c -> c.displayName }

    val productTypes: List<String> by lazy {
        this.dataManager.getAllProductType()
    }

    private val _navigateUpEvent = MutableLiveData<Event<Unit>>()
    val navigateUpEvent: LiveData<Event<Unit>>
        get() = _navigateUpEvent

    val saveButtonEnableLiveData: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(productName) { pName ->
            this.value = !TextUtils.isEmpty(pName) && !TextUtils.isEmpty(productBrand.value)
        }
        addSource(productBrand) { pBrand ->
            this.value = !TextUtils.isEmpty(pBrand) && !TextUtils.isEmpty(productName.value)
        }
    }

    fun saveProduct() {
        viewModelScope.launch {
            info {
                product.value
            }
            dataManager.saveProduct(product.value!!)
        }
        // go back to product list fragment
        navigateUp()
    }

    private fun navigateUp() {
        _navigateUpEvent.value = Event(Unit)
    }

    private fun createProduct(): Product {
        return Product().apply {
            purchaseCurrencyCode = appPreference.purchaseCurrencyCode
            saleCurrencyCode = appPreference.saleCurrencyCode
        }
    }

    fun onPurchaseCurrencyClick(view: View) {
        val currencyDisplayList = currencies.map { c -> c.currencyCode }
        SingleSelectorDialog(view.context, currencyDisplayList).apply {
            title = view.context.resources.getString(R.string.select_currency)
            selected = appPreference.purchaseCurrencyCode
            onSelectionsChanged = { radioButton, dialog ->
                radioButton.text.toString().also {
                    product.value?.purchaseCurrencyCode = it
                    product.notifyObserver()
                    appPreference.purchaseCurrencyCode = it
                }

                dialog.dismiss()
            }
            show()
        }
    }

    fun onSaleCurrencyClick(view: View) {
        val currencyDisplayList = currencies.map { c -> c.currencyCode }
        SingleSelectorDialog(view.context, currencyDisplayList).apply {
            title = view.context.resources.getString(R.string.select_currency)
            selected = appPreference.saleCurrencyCode
            onSelectionsChanged = { radioButton, dialog ->
                radioButton.text.toString().also {
                    product.value?.saleCurrencyCode = it
                    product.notifyObserver()
                    appPreference.saleCurrencyCode = it
                }
                dialog.dismiss()
            }
            show()
        }
    }

    // todo: go to pictures activity and pick one or more pictures, show it and if user save it, save it in the remote(20.06.2020)
    fun onUploadPicturesClick(view: View) {
        Toast.makeText(view.context, "To be done", Toast.LENGTH_SHORT).show()
    }

    // todo: How to handle product type? Maybe first try the project in codelab
    //https://codelabs.developers.google.com/codelabs/firestore-android/#0
//    fun loadAllProductType() {
//        mProductTypeLiveData.addSource(dataManager.getAllProductType()) { mProductTypeLiveData.value = it }
//    }
}
