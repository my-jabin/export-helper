package com.jiujiu.helper.di.module

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jiujiu.helper.util.AppConstant
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    @Named("productCollection")
    fun provideProductCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(AppConstant.PRODUCTSCOLLECTION)
    }

    @Provides
    @Singleton
    @Named("productTypeCollection")
    fun provideProductTypeCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(AppConstant.PRODUCTTYPESCOLLECTION)
    }

    @Provides
    @Singleton
    @Named("customerCollection")
    fun provideCustomerCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(AppConstant.CUSTOMERSCOLLECTION)
    }
}
