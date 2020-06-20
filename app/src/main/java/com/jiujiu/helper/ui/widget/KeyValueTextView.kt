package com.jiujiu.helper.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.jiujiu.helper.R
import com.jiujiu.helper.util.dpToPixel
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textAppearance
import kotlin.math.max

class KeyValueTextView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val keyText: String
    private var valueText: String
    private val valueTextView: TextView
    private val root: View
    private val mOrientation: Int
    private val keyTextAppearance: Int
    private val valueTextAppearance: Int

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.KeyValueTextView,
                0, 0)
        keyText = typedArray.getString(R.styleable.KeyValueTextView_keyText) ?: ""
        valueText = typedArray.getString(R.styleable.KeyValueTextView_valueText) ?: ""
        mOrientation = typedArray.getInt(R.styleable.KeyValueTextView_orientation, HORIZONTAL)
        keyTextAppearance = typedArray.getResourceId(R.styleable.KeyValueTextView_keyTextAppearance, R.style.TextAppearance_keyValueTextView_key)
        valueTextAppearance = typedArray.getResourceId(R.styleable.KeyValueTextView_valueTextAppearance, R.style.TextAppearance_keyValueTextView_value)
        val backgroundRipp = typedArray.getResourceId(R.styleable.KeyValueTextView_android_selectableItemBackground, R.attr.selectableItemBackground)
        typedArray.recycle()

        root = LayoutInflater.from(context)
                .inflate(R.layout.key_value_textview, this, true)

        val keyTextView = root.findViewById<TextView>(R.id.tv_key).apply {
            text = keyText
            textAppearance = keyTextAppearance
        }
        valueTextView = root.findViewById<TextView>(R.id.ed_value).apply {
            text = valueText
            textAppearance = valueTextAppearance
        }

        val layoutParams = valueTextView.layoutParams
        if (mOrientation == HORIZONTAL) {
            (layoutParams as ConstraintLayout.LayoutParams).apply {
                endToEnd = ConstraintSet.PARENT_ID
                topToTop = keyTextView.id
                bottomToBottom = keyTextView.id
            }

            (root.findViewById<View>(R.id.divider_main).layoutParams as MarginLayoutParams).apply {
                topMargin = context.dpToPixel(8).toInt()
            }

        } else {
            (layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = keyTextView.id
                startToStart = keyTextView.id
            }
            (layoutParams as MarginLayoutParams).apply {
                topMargin = context.dpToPixel(4).toInt()
            }
        }

        isClickable = true
        backgroundResource = backgroundRipp
    }


    fun setValueText(value: String?) {
        valueText = value ?: ""
        valueTextView.text = valueText
    }

    fun getValueText(): String = this.valueText


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, max(context.dpToPixel(DEFAULT_HEIGHT).toInt(), measuredHeight))
    }


    companion object {
        private const val DEFAULT_HEIGHT = 48
        private const val HORIZONTAL = 0
        private const val VERTICAL = 1

// todo: two-way binding with keyvaluetextview
//https://developer.android.com/topic/libraries/data-binding/two-way#kotlin
//https://medium.com/androiddevelopers/android-data-binding-2-way-your-way-ccac20f6313


//        @BindingAdapter("valueText")
//        @JvmStatic
//        fun setValueText(view: KeyValueTextView, newValue: String) {
//            if (view.valueText != newValue) {
//                view.valueText = newValue
//            }
//        }
//
//        @BindingAdapter("app:valueTextAttrChanged")
//        @JvmStatic
//        fun setListeners(
//                view: KeyValueTextView,
//                attrChange: InverseBindingListener
//        ) {
//            // Set a listener for click, focus, touch, etc.
//        }
//
//        @InverseBindingAdapter(attribute = "valueText")
//        @JvmStatic
//        fun getValueText(view: KeyValueTextView): String {
//            return view.valueText
//        }

    }


}


