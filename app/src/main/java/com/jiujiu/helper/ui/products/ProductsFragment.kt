package com.jiujiu.helper.ui.products

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestoreException
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.databinding.FragmentProductsBinding
import com.jiujiu.helper.ui.base.BaseFragment
import com.jiujiu.helper.ui.main.MainFragmentDirections
import com.jiujiu.helper.ui.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_products.*
import org.jetbrains.anko.info
import javax.inject.Inject


class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsFragViewModel>() {

    @Inject
    lateinit var mAdapter: ProductsFirestoreRecyclerAdapter

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
        binding.rvProducts.addItemDecoration(SpaceItemDecoration(12, 12, 12, 12))
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
        binding.rvProducts.setHasFixedSize(true)
        ev_products.setRetryListener {
            // todo: what to do when click retry button
            rv_products.visibility = View.VISIBLE
            ev_products.visibility = View.GONE
        }
        val onError = { e: FirebaseFirestoreException ->
            rv_products.visibility = View.GONE
            ev_products.visibility = View.VISIBLE
            ev_products.title = e.localizedMessage
        }
        binding.rvProducts.adapter = mAdapter.toFirebaseRecyclerAdapter(onError)
    }

    private fun setupViewModel() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
//                val action = MainFragmentDirections.actionMainFragmentToProductDetailFragment(null)
//                findNavController().navigate(action)
//                true

                val action = MainFragmentDirections.actionMainFragmentToAddProductFragment()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun getInstance(): ProductsFragment = ProductsFragment()
    }

}
