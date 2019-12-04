package com.jiujiu.helper.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jiujiu.helper.di.ViewModelFactory
import com.jiujiu.helper.di.scope.ViewModelKey
import com.jiujiu.helper.ui.customers.CustomersFragViewModel
import com.jiujiu.helper.ui.customers.addEdit.AddEditCustomerViewModel
import com.jiujiu.helper.ui.main.MainActivityViewModel
import com.jiujiu.helper.ui.main.MainFragViewModel
import com.jiujiu.helper.ui.orders.OrdersFragViewModel
import com.jiujiu.helper.ui.products.ProductsFragViewModel
import com.jiujiu.helper.ui.products.add.ProductAddViewModel
import com.jiujiu.helper.ui.products.details.ProductDetailViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindsMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainFragViewModel::class)
    abstract fun bindsMainFragViewModel(viewModel: MainFragViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductsFragViewModel::class)
    abstract fun bindsProductFragViewModel(viewModel: ProductsFragViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrdersFragViewModel::class)
    abstract fun bindsOrderFragViewModel(viewModel: OrdersFragViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CustomersFragViewModel::class)
    abstract fun bindsCustomerFragViewModel(viewModel: CustomersFragViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddEditCustomerViewModel::class)
    abstract fun bindsAddEditCustomerViewModel(viewModel: AddEditCustomerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    abstract fun bindsProductDetailViewModel(viewModel: ProductDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductAddViewModel::class)
    abstract fun bindsProductAddViewModel(viewModel: ProductAddViewModel): ViewModel


    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
