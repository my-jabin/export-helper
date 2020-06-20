package com.jiujiu.helper.util

import android.widget.AutoCompleteTextView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.jiujiu.helper.data.model.ProductType
import com.jiujiu.helper.ui.widget.AutoCompleteProductTypeAdapter

@BindingAdapter("entries")
fun setEntries(spinner: Spinner, entries: List<Any>?) {
    spinner.setEntries(entries = entries)
}

@BindingAdapter("selectedValue")
fun selectedValue(spinner: Spinner, value: Any?) {
    spinner.setSpinnerValue(value)
}

@BindingAdapter("selectedValueAttrChanged")
fun setInverseBindingListener(spinner: Spinner, listener: InverseBindingListener) {
    spinner.setInverseBindingListener(listener)
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun inverseSelectedValue(spinner: Spinner): Any? {
    return spinner.selectedItem
}

@BindingAdapter("productTypes")
fun setProductTypes(autoCompleteTextView: AutoCompleteTextView, result: Result<List<ProductType>>?) {
    if (result != null && result.isSuccess) {
        val entries = result.getOrDefault(arrayListOf()) as MutableList
        entries.add(ProductType.empty(autoCompleteTextView.context.applicationContext))
        val adapter = AutoCompleteProductTypeAdapter(autoCompleteTextView.context, entries)
        autoCompleteTextView.setAdapter(adapter)
    }
}


//@BindingAdapter("typeId")
//fun selectedValue(view: AutoCompleteTextView, typeId: Int?) {
//    if (view.adapter != null && typeId != null) {
//        if (view.adapter is AutoCompleteProductTypeAdapter) {
//            val adapter = view.adapter as AutoCompleteProductTypeAdapter
//            val position = adapter.findPositionByTypeId(typeId)
//            if (position >= 0) {
//                view.setSelection(position)
//                view.tag = position
//            }
//        }
//    }
//}
//
//@BindingAdapter("typeIdAttrChanged")
//fun setInverseBindingListener(autoCompleteTextView: AutoCompleteTextView, listener: InverseBindingListener) {
//    autoCompleteTextView.setInverseBindingListener(listener)
//}
//
//@InverseBindingAdapter(attribute = "typeId", event = "typeIdAttrChanged")
//fun inverseSelectedValue(view: AutoCompleteTextView): Int? {
//    if (view.adapter != null) {
//        val adapter = view.adapter as? AutoCompleteProductTypeAdapter
//        val type = adapter?.getTypeByPosition(view.tag as Int)
//        return type?.id
//    }
//    return null
//}