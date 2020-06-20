package com.jiujiu.helper.ui.products.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.ui.widget.SingleSelectorDialog
import kotlinx.android.synthetic.main.add_product_part_1.*
import kotlinx.android.synthetic.main.add_product_part_2.*

class AddProductAdapter(fragmentActivity: FragmentActivity, val viewModel: ProductAddViewModel) : FragmentStateAdapter(fragmentActivity) {

    // todo: image upload fragment
    private val fragOne by lazy {
        AddProductStepOne(this.viewModel, fragmentActivity)
    }

    private val fragTwo by lazy {
        AddProductStepTwo(this.viewModel, fragmentActivity)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            fragOne
        else
            fragTwo
    }

    class AddProductStepOne(val viewModel: ProductAddViewModel, val fragmentActivity: FragmentActivity) : Fragment() {
        private lateinit var binding: ViewDataBinding
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            this.binding = DataBindingUtil.inflate(inflater, R.layout.add_product_part_1, container, false)
            this.binding.lifecycleOwner = this
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.product, viewModel.productObj)
            binding.executePendingBindings()

            til_product_brand.addAfterTextChanged {
                this.viewModel.productBrand.value = it
            }
            til_product_name.addAfterTextChanged {
                this.viewModel.productName.value = it
            }
        }
    }

    class AddProductStepTwo(val viewModel: ProductAddViewModel, private val fragmentActivity: FragmentActivity) : Fragment() {
        private lateinit var binding: ViewDataBinding
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            this.binding = DataBindingUtil.inflate(inflater, R.layout.add_product_part_2, container, false)
            this.binding.lifecycleOwner = this
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.product, viewModel.productObj)
//            binding.setVariable(BR.context, view.context)
            binding.executePendingBindings()
            setupViews(view.context)
        }

        private fun setupViews(context: Context) {
            card_product_type.setOnClickListener {
                SingleSelectorDialog(context, viewModel.productTypes).apply {
                    title = context.resources.getString(R.string.select_product_type_title)
                    selected = tv_product_type.text.toString()
                    onSelectionsChanged = { radioButton, dialog ->
                        tv_product_type.text = radioButton.text
                        dialog.dismiss()
                    }
                    show()
                }
            }
//            kvtv_add_product_type.setOnClickListener {
//
//            }
//
//            til_product_purchase_price.isEndIconVisible = false
//            et_product_purchase_price.setOnFocusChangeListener { v, hasFocus ->
//                Log.d("AddProductStepTwo", "has focus = $hasFocus")
//                til_product_purchase_price.isEndIconVisible = hasFocus
//            }
//
//            til_product_purchase_price.setEndIconOnClickListener {
//                CurrencyPicker(
//                        context = context,
//                        currencies = viewModel.currencies,
//                        listener = OnItemClickListener { dialog, item, view, position ->
//                            val product = viewModel.product.value
//                            product?.purchaseCurrency = item.toString()
//                            viewModel.product.value = product
//                            dialog.dismiss()
//                            et_product_purchase_price.requestFocus()
//                        }).show()
//            }
//
//            til_product_sale_price.isEndIconVisible = false
//            til_product_sale_price.setEndIconOnClickListener {
//                CurrencyPicker(
//                        context = context,
//                        currencies = viewModel.currencies,
//                        listener = OnItemClickListener { dialog, item, view, position ->
//                            val product = viewModel.product.value
//                            product?.saleCurrency = item.toString()
//                            viewModel.product.value = product
//                            dialog.dismiss()
//                            et_product_sale_price.requestFocus()
//                        }).show()
//            }
//
//            et_product_sale_price.setOnFocusChangeListener { v, hasFocus ->
//                til_product_sale_price.isEndIconVisible = hasFocus
//            }
        }
    }
}