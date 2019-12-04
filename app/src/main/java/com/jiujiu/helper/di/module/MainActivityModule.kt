package com.jiujiu.helper.di.module

import com.jiujiu.helper.di.module.subModules.*
import com.jiujiu.helper.ui.customers.CustomersFragment
import com.jiujiu.helper.ui.customers.addEdit.AddEditCustomerFragment
import com.jiujiu.helper.ui.main.MainFragment
import com.jiujiu.helper.ui.orders.OrdersFragment
import com.jiujiu.helper.ui.products.ProductsFragment
import com.jiujiu.helper.ui.products.add.AddProductFragment
import com.jiujiu.helper.ui.products.details.ProductDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun bindsMainFragment(): MainFragment

    @ContributesAndroidInjector(modules = [ProductsFragmentModule::class])
    abstract fun bindsProductsFragment(): ProductsFragment

    @ContributesAndroidInjector(modules = [ProductDetailFragmentModule::class])
    abstract fun bindsProductDetailFragment(): ProductDetailFragment

    @ContributesAndroidInjector(modules = [ProductAddFragmentModule::class])
    abstract fun bindsProductAddFragment(): AddProductFragment

    @ContributesAndroidInjector(modules = [OrderFragmentModule::class])
    abstract fun bindsOrdersFragment(): OrdersFragment

    @ContributesAndroidInjector(modules = [CustomerFragmentModule::class])
    abstract fun bindsCustomerFragment(): CustomersFragment

    @ContributesAndroidInjector(modules = [AddEditCustomerFragmentModule::class])
    abstract fun bindsAddEditCustomerFragment(): AddEditCustomerFragment

}
