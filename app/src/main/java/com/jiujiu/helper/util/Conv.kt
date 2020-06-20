package com.jiujiu.helper.util

import androidx.databinding.InverseMethod
import com.jiujiu.helper.R

object Conv {

    @InverseMethod("stringToDouble")
    @JvmStatic
    fun doubleToString(value: Double?): String {
        return value?.toString() ?: ""
    }

    @JvmStatic
    fun stringToDouble(value: String): Double? {
        if (value.isEmpty() || value.endsWith("."))
            return null
        if (value.startsWith(".") && value.length == 1)
            return null
        return value.toDouble()
    }

    //    @InverseMethod("iconAsTextToCurrencyCode")
    @JvmStatic
    fun currencyCodeToIconAsText(value: String?): Int {
        return when (value) {
            "CNY" -> R.string.icon_currency_cny
            "EUR" -> R.string.icon_currency_euro
            "USD" -> R.string.icon_currency_usd
            else -> R.string.icon_currency_gbp
//            "CNY" -> context.getString(R.string.icon_currency_cny)
//            "EUR" -> context.getString(R.string.icon_currency_euro)
//            "USD" -> context.getString(R.string.icon_currency_usd)
//            else -> context.getString(R.string.icon_currency_gbp)
        }
    }

//    @JvmStatic
//    fun iconAsTextToCurrencyCode(oldValue: String?, value: String?, context:Context): String {
//        return when (value) {
//            view.context.getString(R.string.icon_currency_cny) -> "CNY"
//            view.context.getString(R.string.icon_currency_euro) -> "EUR"
//            view.context.getString(R.string.icon_currency_usd) -> "USD"
//            else -> "GBP"
//        }
//    }

}