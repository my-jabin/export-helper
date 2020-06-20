package com.jiujiu.helper.ui.widget

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.orhanobut.dialogplus.*
import java.util.*

class CurrencyPicker(val context: Context, val currencies: List<Currency> = Currency.getAvailableCurrencies().toList(), listener: OnItemClickListener? = null) {

    val builder: DialogPlusBuilder = DialogPlus.newDialog(context).apply {
        isCancelable = false
        isExpanded = false
        setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
        setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
        setGravity(Gravity.CENTER)
        setHeader(R.layout.currency_picker_header)
        setContentHolder(ListHolder())
        setInAnimation(R.anim.fade_in_center)
        setOutAnimation(R.anim.fade_out_center)
        adapter = CurrencyAdapter()
    }
    private val dialog: DialogPlus


    init {
        builder.onItemClickListener = listener

        dialog = builder.create().apply {
            OnBackPressListener { dismiss() }
        }

    }

    fun show() {
        dialog.show()
    }

    inner class CurrencyAdapter : ArrayAdapter<Currency>(context, 0, currencies) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding: ViewDataBinding = if (convertView == null) {
                DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.currency_picker_content, parent, false)
            } else {
                DataBindingUtil.bind(convertView)!!
            }
            binding.setVariable(BR.currency, currencies[position])
            return binding.root
        }
    }


}