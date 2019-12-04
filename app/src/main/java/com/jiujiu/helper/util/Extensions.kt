package com.jiujiu.helper.util

import android.content.Context
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.text.Editable
import android.text.TextPaint
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.databinding.InverseBindingListener
import com.github.promeg.pinyinhelper.Pinyin
import com.jiujiu.helper.R


val TAG: String = "LOGTAG"

fun View.isRtl() = layoutDirection == View.LAYOUT_DIRECTION_RTL

fun Spinner.setEntries(entries: List<Any>?) {
    if (entries != null) {
        val arrayAdapter = ArrayAdapter(
                context,
                R.layout.dropdown_menu_popup_item,
                //android.R.layout.simple_spinner_item
                entries)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.adapter = arrayAdapter
    }
}

fun Spinner.setSpinnerValue(value: Any?) {
    if (this.adapter != null) {
        val position = (this.adapter as ArrayAdapter<Any>).getPosition(value)
        this.setSelection(position, false)
        tag = position
    }
}

fun Spinner.setInverseBindingListener(listener: InverseBindingListener) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (tag != position) {
                listener.onChange()
                tag = position
            }
        }
    }
}

fun AutoCompleteTextView.setInverseBindingListener(listener: InverseBindingListener) {
    this.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        if (tag != position) {
            tag = position
            listener.onChange()
        }
    }
}

fun TextPaint.center(width: Float, height: Float): PointF {
    this.textAlign = Paint.Align.CENTER
    val textHeight = this.descent() - this.ascent()
    val textOffset = textHeight / 2 - this.descent()
    val bounds = RectF(0f, 0f, width, height)
    return PointF(bounds.centerX(), bounds.centerY() + textOffset)
}

fun Context.spToPixel(sp: Float) = resources.displayMetrics.scaledDensity * sp
fun Context.pixelToSp(pixel: Float) = pixel / resources.displayMetrics.scaledDensity
fun Context.dpToPixel(dp: Int): Float = dp * resources.displayMetrics.density
fun Context.pixelToDp(pixel: Float) = pixel / resources.displayMetrics.density

fun Char.toPinyin() = Pinyin.toPinyin(this)

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun String.isEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return !TextUtils.isEmpty(this) && this.length > 6
}