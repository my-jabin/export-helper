package com.jiujiu.helper.ui.customers

import android.os.Bundle
import android.view.View
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.databinding.FragmentCustomersBinding
import com.jiujiu.helper.ui.base.BaseFragment
import org.jetbrains.anko.debug


class CustomersFragment : BaseFragment<FragmentCustomersBinding, CustomersFragViewModel>() {

    override val bindingVariableId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_customers

    override val viewModelType: Class<CustomersFragViewModel>
        get() = CustomersFragViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        debug("onViewCreated: ")
    }

    private fun setupViewModel() {}

    companion object {
        fun getInstance(): CustomersFragment = CustomersFragment()
    }

}
