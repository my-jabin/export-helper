package com.jiujiu.helper.ui.orders


import android.os.Bundle
import android.view.View
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.databinding.FragmentOrdersBinding
import com.jiujiu.helper.ui.base.BaseFragment
import org.jetbrains.anko.info


class OrdersFragment : BaseFragment<FragmentOrdersBinding, OrdersFragViewModel>() {

    override val bindingVariableId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_orders

    override val viewModelType: Class<OrdersFragViewModel>
        get() = OrdersFragViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        info("onViewCreated: ")
    }

    private fun setupViewModel() {}

    companion object {
        fun getInstance(): OrdersFragment = OrdersFragment()
    }

}
