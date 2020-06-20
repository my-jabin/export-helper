package com.jiujiu.helper.ui.widget

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.get
import com.jiujiu.helper.R
import com.jiujiu.helper.util.dpToPixel
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.DialogPlusBuilder
import com.orhanobut.dialogplus.OnBackPressListener
import com.orhanobut.dialogplus.ViewHolder
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

class SingleSelectorDialog(context: Context, private val selections: List<String>) {

    val builder: DialogPlusBuilder = DialogPlus.newDialog(context).apply {
        isCancelable = true
        isExpanded = false
        setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
        setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
        setGravity(Gravity.CENTER)
        setHeader(R.layout.single_selector_header)
        setInAnimation(R.anim.fade_in_center)
        setOutAnimation(R.anim.fade_out_center)
    }

    lateinit var dialog: DialogPlus

    var title: String? = null

    var selected: String? = null

    var onSelectionsChanged: ((radioButton: RadioButton, dialog: DialogPlus) -> Unit)? = null

    private val radioGroup: RadioGroup

    init {
        val params = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT)
        val margin = context.dpToPixel(10).toInt()
        (params as ViewGroup.MarginLayoutParams).setMargins(margin, margin, margin, margin)
        radioGroup = RadioGroup(context).apply {
            orientation = RadioGroup.VERTICAL
            selections.forEachIndexed { index, sel ->
                val radioButton = RadioButton(context).apply {
                    text = sel
                    id = index
                    layoutParams = params
                }
                addView(radioButton)
            }
            onCheckedChange { group, checkedId ->
                if (checkedId >= 0) {
                    val checkedRadioButton = group?.get(checkedId) as RadioButton
                    if (selected != checkedRadioButton.text)
                        onSelectionsChanged?.invoke(checkedRadioButton, dialog)
                }
            }
        }
        builder.setContentHolder(ViewHolder(radioGroup))
    }

    fun show() {
        dialog = builder.create().apply {

            radioGroup.check(selections.indexOf(selected))

            OnBackPressListener { dismiss() }

            if (title != null) {
                headerView.findViewById<TextView>(R.id.single_selector_title).text = title
            }

            show()
        }
    }

}
