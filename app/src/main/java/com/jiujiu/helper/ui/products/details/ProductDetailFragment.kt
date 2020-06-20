package com.jiujiu.helper.ui.products.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.databinding.ProductDetailBinding
import com.jiujiu.helper.ui.base.BaseFragment
import com.jiujiu.helper.ui.widget.WarningDialog
import org.jetbrains.anko.info

class ProductDetailFragment : BaseFragment<ProductDetailBinding, ProductDetailViewModel>() {

    override val bindingVariableId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.product_detail

    override val viewModelType: Class<ProductDetailViewModel>
        get() = ProductDetailViewModel::class.java

    private val args: ProductDetailFragmentArgs by navArgs()

    private var productId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo:  show product detail. optimize UI
        // todo: modify product? how? go to AddProductFragment?
        productId = args.productId
        if (args.productId == null) {
//            AddProductDialog().show(this.childFragmentManager, "add product")
//            AddProductDialog(this.context!!).show()
        } else {
            viewModel.loadProductById(productId!!)
        }
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.productVOLiveData.observe(this.viewLifecycleOwner, Observer {

            it?.apply {
                info("product detail is refreshing")
                info(it.toString())
                binding.product = it
            }
        })

        viewModel.failureEventLiveData.observe(this.viewLifecycleOwner, Observer {
            Toast.makeText(this.context, it?.message!!, Toast.LENGTH_LONG).show()
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // todo: edit product in AddProductFragment?
        inflater.inflate(R.menu.product_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_product_delete -> {
                WarningDialog(context!!, message = "Are you sure you want to delete this product?").apply {
                    onConfirmClick {
                        viewModel.deleteProductById(productId!!)
                    }
                    show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        // dynamically change option menu
        menu.findItem(R.id.menu_add).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

}
