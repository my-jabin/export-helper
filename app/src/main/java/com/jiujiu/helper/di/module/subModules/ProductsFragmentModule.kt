package com.jiujiu.helper.di.module.subModules

import com.google.firebase.firestore.CollectionReference
import com.jiujiu.helper.ui.products.ProductsFirestoreRecyclerAdapter
import com.jiujiu.helper.ui.products.ProductsFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ProductsFragmentModule {

    @Provides
    fun provideProductFirebaseRecycAdapter(@Named("productCollection") collection: CollectionReference, context: ProductsFragment): ProductsFirestoreRecyclerAdapter {
        return ProductsFirestoreRecyclerAdapter(collection, context)
    }
}
