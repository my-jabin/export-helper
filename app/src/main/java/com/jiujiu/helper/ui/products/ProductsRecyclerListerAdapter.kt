package com.jiujiu.helper.ui.products

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import com.jiujiu.helper.R
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.databinding.ProductItemBinding
import com.jiujiu.helper.ui.base.BaseRecyclerListAdapter

class ProductsRecyclerListerAdapter : BaseRecyclerListAdapter<Product, ProductItemBinding>(diffProduct) {

    override val itemLayoutId: Int
        get() = R.layout.product_item

    override fun bindViewHolder(binding: ProductItemBinding, position: Int) {
        val p = getItem(position) ?: return
        with(binding) {
            product = p
            setItemClickListener { v -> onProductItemClick(v, p) }
        }
    }

    private fun onProductItemClick(v: View, @NonNull p: Product) {
//        val action = MainFragmentDirections.actionMainFragmentToProductDetailFragment(p.id!!)
//        action.title = p.name ?: ""
//        v.findNavController().navigate(action)
    }
}

val diffProduct: DiffUtil.ItemCallback<Product> = object : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.brand == newItem.brand &&
                oldItem.model == newItem.model &&
                oldItem.salePrice == newItem.salePrice
    }
}