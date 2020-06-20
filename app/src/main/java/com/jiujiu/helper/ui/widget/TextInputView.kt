package com.jiujiu.helper.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.jiujiu.helper.R
import com.jiujiu.helper.util.afterTextChanged
import com.jiujiu.helper.util.dpToPixel
import org.jetbrains.anko.textAppearance
import kotlin.math.max


class TextInputView(context: Context, attrs: AttributeSet)
    : ConstraintLayout(context, attrs) {

    private val mHint: String?
    private var mText: String?
    private val mHintAppearance: Int
    private val mTextAppearance: Int
    private val root: View
    private val textEditView: EditText

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.TextInputView,
                0, 0)
        mHint = typedArray.getString(R.styleable.TextInputView_hint)
        mText = typedArray.getString(R.styleable.TextInputView_text)
        mHintAppearance = typedArray.getResourceId(R.styleable.TextInputView_hintTextAppearance, R.style.TextAppearance_textInputView_hint)
        mTextAppearance = typedArray.getResourceId(R.styleable.TextInputView_textAppearance, R.style.TextAppearance_textInputView_Text)

        typedArray.recycle()
        root = LayoutInflater.from(context)
                .inflate(R.layout.text_input_view, this, true)

        val hintView = root.findViewById<TextView>(R.id.tv_hint).apply {
            text = mHint
            textAppearance = mHintAppearance
        }
        textEditView = root.findViewById<EditText>(R.id.ed_text).apply {
            setText(mText)
            textAppearance = mTextAppearance
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, max(context.dpToPixel(DEFAULT_HEIGHT).toInt(), measuredHeight))
    }

    fun addAfterTextChanged(addAfterTextChanged: (String) -> Unit) {
        this.textEditView.afterTextChanged {
            addAfterTextChanged(it)
        }
    }

    private fun setText(value: String?) {
        mText = value ?: ""
        textEditView.setText(mText)
    }

    private fun getText(): String? = this.mText


    companion object {

        private const val DEFAULT_HEIGHT = 48

        @JvmStatic
        @BindingAdapter("text")
        fun setText(view: TextInputView, value: String?) {
            if (value != view.getText()) view.setText(value)
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "text")
        fun getText(view: TextInputView): String? {
            return view.textEditView.text.toString()
        }

        @JvmStatic
        @BindingAdapter(value = ["textAttrChanged"])
        fun setListener(view: TextInputView, textAttrChanged: InverseBindingListener?) {
            view.textEditView.afterTextChanged { textAttrChanged?.onChange() }
        }


    }
}
