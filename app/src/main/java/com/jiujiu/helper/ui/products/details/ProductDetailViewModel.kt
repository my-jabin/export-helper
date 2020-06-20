package com.jiujiu.helper.ui.products.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.data.model.ProductVO
import com.jiujiu.helper.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(
        private val context: Context,
        dataManager: DataManager
) : BaseViewModel(dataManager) {

    //    private var _productLiveData: MediatorLiveData<Product> = MediatorLiveData()
    private val _productVOLiveData: MediatorLiveData<ProductVO> = MediatorLiveData()
    val productVOLiveData: LiveData<ProductVO>
        get() = _productVOLiveData

    private var _failureEventLiveData: MediatorLiveData<Throwable?> = MediatorLiveData()
    val failureEventLiveData: LiveData<Throwable?>
        get() = _failureEventLiveData

    init {
//        _productVOLiveData.addSource(_productLiveData) { product ->
//            run {
//                // start a coroutine, working on the same context as ViewModel
//                viewModelScope.launch {
//                    // createProductVO is a suspend function, it suspends here but does not block (main) thread
//                    val productVO = createProductVO(product)
//                    // once productVO is ready, post it
//                    _productVOLiveData.postValue(productVO)
//                }
//            }
//        }
    }


    fun deleteProductById(productId: String) {
        viewModelScope.launch {
            // todo: delete product by id.
            // by calling deleteProductById, the product is already deleted in the remote(firebase)
            // but  ProductRepository.getProductById(productId) will be triggered when the document(product) is added,modified and deleted.
            // 这里又有自定义的 FirebaseLiveData 对象，这个对象是把firebase的snapshot 转化为LiveData
            // 但是snapshot有一个EventListener， 当added, modified, and deleted的时候会启动. 但是deleted的时候我不想启动，或者可以启动，但是返回null值且不能对其他地方有影响
            // 现在的问题是 FirebaseLiveData.getFirstAsLiveData(): 这个方法可能会报错。需要进一步测试
            // 所以下一步就是要 完成删除product的操作
            dataManager.deleteProductById(productId)
        }

    }

//    private fun getTypeById(id: Long?): String? {
//        return dataManager.getTypeById(id).name
//    }

    fun loadProductById(productId: String) {
        viewModelScope.launch {
            val product = dataManager.getProductById(productId)
            _productVOLiveData.addSource(product) {
                if (it.isSuccess) {
                    _productVOLiveData.postValue(it.getOrNull()?.toProductVo(context))
                }
            }

            _failureEventLiveData.addSource(product) {
                if (it.isFailure) {
                    _failureEventLiveData.postValue(it.exceptionOrNull())
                }
            }
        }
    }
//            _productLiveData.addSource() { _productLiveData.postValue(it) }
//        }
//    }

}
