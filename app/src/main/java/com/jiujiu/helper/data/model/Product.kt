package com.jiujiu.helper.data.model

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.configuration
import java.util.*

data class Product(
        var id: String? = null,

        var userUID: String? = FirebaseAuth.getInstance().uid,

        var name: String? = null,

        var brand: String? = null,

        var brandZh: String? = null,

        var nameZh: String? = null,

        var model: String? = null,

        var modelZh: String? = null,

        var type: String? = null,

        var description: String? = null,

        var netWeight: Double? = null,

        var salePrice: Double? = null,

        var saleCurrencyCode: String? = "CNY", // chinese currency code

        var purchasePrice: Double? = null,

        var purchaseCurrencyCode: String? = Currency.getInstance(Locale.getDefault()).currencyCode,

        var madeIn: String? = "Germany",

        var purchaseApproach: String? = null
) {

    fun toProductVo(context: Context): ProductVO {
        return ProductVO(this, context.configuration.locales.get(0))
    }

//    override fun toString(): String {
//        return "name[${name}],nameZh[${nameZh}],[${name}],name[${name}],name[${name}],name[${name}],"
//    }
}


