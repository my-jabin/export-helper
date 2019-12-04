package com.jiujiu.helper.ui.widget

import android.content.Context
import android.widget.ArrayAdapter
import com.jiujiu.helper.R
import com.jiujiu.helper.data.model.ProductType

class AutoCompleteProductTypeAdapter constructor(context: Context, private val types: List<ProductType>)

    : ArrayAdapter<ProductType>(context, R.layout.dropdown_menu_popup_item, types) {

    fun findPositionByTypeId(id: Int): Int {
        val type = types.find { type -> type.id == id }
        return types.indexOf(type)
    }

    fun getTypeByPosition(position: Int): ProductType? {
        return if (position >= 0 && position < types.size) types[position]
        else null
    }

}