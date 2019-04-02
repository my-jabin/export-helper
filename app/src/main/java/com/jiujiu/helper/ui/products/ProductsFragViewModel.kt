package com.jiujiu.helper.ui.products

import androidx.lifecycle.LiveData
import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.ui.base.BaseViewModel
import javax.inject.Inject

class ProductsFragViewModel @Inject constructor(
        dataManager: DataManager
) : BaseViewModel(dataManager) {

    val productLiveData: LiveData<List<Product>> by lazy {
        dataManager.allProducts
    }

}
