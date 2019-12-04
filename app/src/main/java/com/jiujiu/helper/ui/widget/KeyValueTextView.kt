package com.jiujiu.helper.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.jiujiu.helper.R

class KeyValueTextView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private val keyText: String?
    private val valueText: String?
    private val root: View

    init {

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.KeyValueTextView,
                0, 0)
        keyText = typedArray.getString(R.styleable.KeyValueTextView_keyText) ?: ""
        valueText = typedArray.getString(R.styleable.KeyValueTextView_valueText) ?: ""
        typedArray.recycle()

        root = LayoutInflater.from(context)
                .inflate(R.layout.key_value_textview, this, true)

        root.findViewById<TextView>(R.id.tv_key).apply {
            text = keyText
        }
        root.findViewById<TextView>(R.id.tv_value).apply {
            text = valueText
        }
    }


    fun setValueText(value: String?) {
        root.findViewById<TextView>(R.id.tv_value).apply {
            text = value ?: ""
        }
    }

}
