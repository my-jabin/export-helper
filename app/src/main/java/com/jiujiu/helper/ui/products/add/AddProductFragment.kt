package com.jiujiu.helper.ui.products.add


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.databinding.ProductAddBinding
import com.jiujiu.helper.ui.base.BaseFragment
import com.jiujiu.helper.util.EventObserver


class AddProductFragment : BaseFragment<ProductAddBinding, ProductAddViewModel>() {

    override val bindingVariableId: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.product_add
    override val viewModelType: Class<ProductAddViewModel>
        get() = ProductAddViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadAllProductType()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.navigateUpEvent.observe(this, EventObserver {
            findNavController().navigateUp()
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        // dynamically change option menu
        menu.findItem(R.id.menu_add).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                viewModel.saveProduct()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
