package com.jiujiu.helper.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.jiujiu.helper.R


class TextInputEditTextFix(context: Context, attrs: AttributeSet)
    : TextInputEditText(context, attrs) {

    private var suffix: String?

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.TextInputEditTextFix,
                0, 0)
        suffix = typedArray.getString(R.styleable.TextInputEditTextFix_suffix) ?: ""
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = currentHintTextColor
        paint.textSize = textSize
        canvas?.drawText(suffix, measuredWidth - paint.measureText(suffix) - paddingRight, baseline.toFloat(), paint)
    }

}