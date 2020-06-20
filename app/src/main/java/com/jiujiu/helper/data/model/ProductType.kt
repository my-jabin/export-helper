package com.jiujiu.helper.data.model

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.jiujiu.helper.R

data class ProductType(
        var id: String? = null,
        var name: String? = null,
        var userUID: String? = FirebaseAuth.getInstance().uid,
        var frequency: Int = 0   // how often does a user select this type
        // 0: normal 1: often -1: seldom
) {

    override fun toString(): String {
        return name ?: ""
    }

    companion object {
        fun empty(context: Context) = ProductType(name = context.getString(R.string.text_add_product_type))
    }
}

