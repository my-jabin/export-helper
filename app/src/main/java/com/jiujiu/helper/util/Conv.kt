package com.jiujiu.helper.util

import androidx.databinding.InverseMethod

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

}