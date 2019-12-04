package com.jiujiu.helper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductType(
        val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    override fun toString(): String {
        return name
    }
}

