package com.jiujiu.helper.ui.products.add


import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.databinding.ProductAdd2Binding
import com.jiujiu.helper.ui.base.BaseFragment
import com.jiujiu.helper.util.EventObserver
import kotlinx.android.synthetic.main.product_add2.*
import kotlin.math.max


class AddProductFragment : BaseFragment<ProductAdd2Binding, ProductAddViewModel>() {

    override val bindingVariableId: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.product_add2
    override val viewModelType: Class<ProductAddViewModel>
        get() = ProductAddViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.loadAllProductType()
        setUpView()
        setUpViewPager()
        setupViewModel()
    }

    private fun setUpView() {
        bt_add_product_previous.setOnClickListener {
            viewPager.setCurrentItem(max(0, viewPager.currentItem - 1), true)
            bt_add_product_next.visibility = View.VISIBLE
            bt_add_product_save.visibility = View.INVISIBLE
        }

        bt_add_product_next.setOnClickListener {
            bt_add_product_save.visibility = View.VISIBLE
            bt_add_product_next.visibility = View.INVISIBLE
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        }


    }

    private fun setUpViewPager() {
        viewPager.adapter = AddProductAdapter(this.activity!!, this.viewModel)
        viewPager.isUserInputEnabled = false
    }

    private fun setupViewModel() {
        viewModel.navigateUpEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })

        viewModel.saveButtonEnableLiveData.observe(viewLifecycleOwner, Observer {
            bt_add_product_save.isEnabled = it
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.add_product_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        // dynamically change option menu
        menu.findItem(R.id.menu_add).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.menu_save -> {
//                viewModel.saveProduct()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}

