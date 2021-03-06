package com.jiujiu.helper.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.jiujiu.helper.ui.customers.CustomersFragment
import com.jiujiu.helper.ui.orders.OrdersFragment
import com.jiujiu.helper.ui.products.ProductsFragment

class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ProductsFragment.getInstance()
            1 -> OrdersFragment.getInstance()
            2 -> CustomersFragment.getInstance()
            else -> throw IllegalArgumentException("Error fragment in viewpager")
        }
    }

    override fun getCount(): Int {
        return 3
    }
}
