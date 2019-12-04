package com.jiujiu.helper.ui.products.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.data.model.ProductVO
import com.jiujiu.helper.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.configuration
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(
        private val context: Context,
        dataManager: DataManager
) : BaseViewModel(dataManager) {

    private var _productLiveData: MediatorLiveData<Product> = MediatorLiveData()
    private val _productVOLiveData: MediatorLiveData<ProductVO> = MediatorLiveData()
    val productVOLiveData: LiveData<ProductVO>
        get() = _productVOLiveData

    init {
        _productVOLiveData.addSource(_productLiveData) { product ->
            run {
                // start a coroutine, working on the same context as ViewModel
                viewModelScope.launch {
                    // createProductVO is a suspend function, it suspends here but does not block (main) thread
                    val productVO = createProductVO(product)
                    // once productVO is ready, post it
                    _productVOLiveData.postValue(productVO)
                }
            }
        }
    }

    private suspend fun createProductVO(product: Product): ProductVO? = withContext(Dispatchers.IO) {
        product.transform(context.configuration.locales.get(0)).apply {
            type = getTypeById(this.typeId?.toLong())
        }
    }


    private fun getTypeById(id: Long?): String? {
        return dataManager.getTypeById(id).name
    }

    fun loadProductById(productId: Long?) {
        viewModelScope.launch {
            _productLiveData.addSource(dataManager.getProductById(productId)) { _productLiveData.postValue(it) }
        }
    }

}
