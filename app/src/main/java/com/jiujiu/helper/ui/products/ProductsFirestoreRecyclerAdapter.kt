package com.jiujiu.helper.ui.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import com.jiujiu.helper.R
import com.jiujiu.helper.data.model.Product
import com.jiujiu.helper.databinding.ProductItemBinding
import com.jiujiu.helper.ui.base.BaseRecyclerListAdapter
import com.jiujiu.helper.ui.main.MainFragmentDirections
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

class ProductsFirestoreRecyclerAdapter @Inject constructor(productCollection: CollectionReference, owner: LifecycleOwner) : AnkoLogger {

    private val layoutId = R.layout.product_item

    private lateinit var mAdapter: FirestoreRecyclerAdapter<Product, ProductItemViewHolder>

    private val uid: String? = FirebaseAuth.getInstance().currentUser?.uid

    private val options: FirestoreRecyclerOptions<Product>

    init {
        options = FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(productCollection.whereEqualTo("userUID", uid)) { snapshot ->
                    val product = snapshot.toObject(Product::class.java)
                    product?.id = snapshot.id
                    product!!
                }
                .setLifecycleOwner(owner)
                .build()

    }

    fun toFirebaseRecyclerAdapter(onError: ((e: FirebaseFirestoreException) -> Unit)? = null): FirestoreRecyclerAdapter<Product, ProductItemViewHolder> {

        this.mAdapter = object : FirestoreRecyclerAdapter<Product, ProductItemViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
                return ProductItemViewHolder(
                        DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
                )
            }

            override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int, model: Product) {
                val p = getItem(position)
                with(holder.binding) {
                    product = p
                    setItemClickListener { v -> onProductItemClick(v, p) }
                }
            }

            override fun onDataChanged() {
                info {
                    "Products have been changed"
                }
            }

            override fun onError(e: FirebaseFirestoreException) {
                onError?.invoke(e)
            }

            private fun onProductItemClick(v: View, @NonNull p: Product) {
                val action = MainFragmentDirections.actionMainFragmentToProductDetailFragment(p.id)
//                action.title = p.name ?: ""
                v.findNavController().navigate(action)
            }

        }

        return mAdapter
    }

    class ProductItemViewHolder(binding: ProductItemBinding) : BaseRecyclerListAdapter.BaseViewHolder<ProductItemBinding>(binding)

}