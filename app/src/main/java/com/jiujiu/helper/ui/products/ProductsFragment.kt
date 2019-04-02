package com.jiujiu.helper.ui.products

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.databinding.FragmentProductsBinding
import com.jiujiu.helper.ui.base.BaseFragment
import org.jetbrains.anko.info


class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsFragViewModel>() {

    private lateinit var mAdapter: ProductsRecyclerListerAdapter

    override val bindingVariableId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_products

    override val viewModelType: Class<ProductsFragViewModel>
        get() = ProductsFragViewModel::class.java


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info { "onCreated" }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupViewModel()
        info("onViewCreated: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        info("onDestroyView:")
    }

    override fun onDestroy() {
        super.onDestroy()
        info("onDestroy")
    }

    private fun setupLayout() {
        mAdapter = ProductsRecyclerListerAdapter()
        binding.rvProducts.adapter = mAdapter
    }

    private fun setupViewModel() {
        viewModel.productLiveData.observe(this, Observer { mAdapter.submitList(it) })
    }

    companion object {
        fun getInstance(): ProductsFragment = ProductsFragment()
    }

}
