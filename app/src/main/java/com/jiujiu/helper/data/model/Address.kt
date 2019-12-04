package com.jiujiu.helper.data.model

import androidx.room.ColumnInfo

data class Address(
        var street: String?,
        var district: String?,
        var city: String?,
        var province: String?,
        @ColumnInfo(name = "post_code") val postCode: Int? = null,
        var state: String? = "中国"
) {
    override fun toString(): String {
        return "$state$province$city$district$street"
    }
}