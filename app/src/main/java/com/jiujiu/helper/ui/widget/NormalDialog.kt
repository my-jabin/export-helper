package com.jiujiu.helper.ui.widget

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.jiujiu.helper.R
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.DialogPlusBuilder
import com.orhanobut.dialogplus.OnBackPressListener
import com.orhanobut.dialogplus.ViewHolder


class WarningDialog(context: Context, var message: String? = null) {

    private val builder: DialogPlusBuilder = DialogPlus.newDialog(context).apply {
        isCancelable = false
        setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
        setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
        setGravity(Gravity.CENTER)
        setHeader(R.layout.warning_dialog_header)
        setContentHolder(ViewHolder(R.layout.warning_dialog_content))
        setFooter(R.layout.warning_dialog_footer)
        setInAnimation(R.anim.fade_in_center)
        setOutAnimation(R.anim.fade_out_center)
    }
    private val dialog: DialogPlus

    private val confirmButton: Button
    private val cancelButton: Button

    init {
        dialog = builder.create().apply {
            OnBackPressListener { dismiss() }
        }

        val holderView = dialog.holderView
        val contentTextView = holderView.findViewById<TextView>(R.id.warning_dialog_context_view)
        contentTextView.text = message

        confirmButton = dialog.footerView.findViewById<Button>(R.id.bt_warning_dialog_confirm).also {
            it.setOnClickListener {
                dialog.dismiss()
            }
        }
        cancelButton = dialog.footerView.findViewById<Button>(R.id.bt_warning_dialog_cancel).also {
            it.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    fun onConfirmClick(confirmListener: () -> Unit) {
        confirmButton.setOnClickListener {
            confirmListener.invoke()
            this.dialog.dismiss()
        }
    }

    fun onCancelClick(cancelListener: () -> Unit) {
        cancelButton.setOnClickListener {
            cancelListener.invoke()
            this.dialog.dismiss()
        }
    }

    fun show() {
        dialog.show()
    }
}