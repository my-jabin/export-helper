package com.jiujiu.helper.data.model

import java.util.*

class ProductVO(product: Product, locale: Locale) {

    val displayName = if (locale == Locale.CHINESE) product.brandZh + product.nameZh + product.modelZh
    else "${product.brand} ${product.name} ${product.model}"

    val displaySalePrice = "${product.saleCurrency?.symbol}${product.salePrice}"

    val displayPurchasePrice = "${product.purchaseCurrency?.symbol}${product.purchasePrice}"

    val brand = product.brand

    val brandZh = product.brandZh

    val name = product.name

    val nameZh = product.nameZh

    val model = product.model

    val modelZh = product.modelZh

    val netWeight: String = "${product.netWeight}kg"

    val typeId = product.typeId

    var type: String? = null

    val madeIn = product.madeIn

    val description = product.description

    val approach = product.purchaseApproach
}