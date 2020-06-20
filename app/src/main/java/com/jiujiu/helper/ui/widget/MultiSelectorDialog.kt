package com.jiujiu.helper.ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.jiujiu.helper.R
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.DialogPlusBuilder
import com.orhanobut.dialogplus.ListHolder
import com.orhanobut.dialogplus.OnBackPressListener
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

class MultiSelectorDialog(private val context: Context, private val mSelections: List<String>) {

    val builder: DialogPlusBuilder = DialogPlus.newDialog(context).apply {
        isCancelable = true
        isExpanded = false
        setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
        setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
        setGravity(Gravity.CENTER)
        setHeader(R.layout.multi_selector_header)
        setContentHolder(ListHolder())
        setInAnimation(R.anim.fade_in_center)
        setOutAnimation(R.anim.fade_out_center)
        adapter = MultiSelectorAdapter()
    }

    private var mTitle: String? = null
    private var itemCheckedListener: ((String, Boolean) -> Unit)? = null
    private val mSelected: MutableMap<Int, String> = mutableMapOf()
    private var onSelectionsChanged: ((selected: Map<Int, String>) -> Unit)? = null

    fun show() {
        builder.create().apply {
            OnBackPressListener { dismiss() }
            if (mTitle != null) {
                headerView.findViewById<TextView>(R.id.multi_selector_title).text = mTitle
            }
            headerView.findViewById<MaterialButton>(R.id.multi_selector_header_dismiss).setOnClickListener {
                dismiss()
            }
            show()
        }

//        if (dialog == null) {
//            dialog = createDialog()
//        }
//        dialog!!.show()
//
//        if (mTitle != null) {
//            dialog!!.headerView.findViewById<TextView>(R.id.multi_selector_title).text = mTitle
//        }
//        dialog!!.headerView.findViewById<MaterialButton>(R.id.multi_selector_header_dismiss).setOnClickListener {
//            dialog?.dismiss()
//        }
    }

//    private fun createDialog(): DialogPlus {
//        return builder.create().apply {
//            OnBackPressListener { dismiss() }
//        }
//    }

    fun setTitle(title: String) {
        mTitle = title
    }

    fun setSelected(selected: List<String>) {
        selected.filter { s -> !TextUtils.isEmpty(s) }.forEach { s ->
            mSelected.putIfAbsent(this.mSelections.indexOf(s), s)
        }
        Log.d(this.javaClass.name, mSelected.toString())
    }

    fun setOnItemCheckedListener(listener: ((text: String, isChecked: Boolean) -> Unit)) {
        this.itemCheckedListener = listener
    }

    fun setOnSelectionsChanged(listener: (selected: Map<Int, String>) -> Unit) {
        this.onSelectionsChanged = listener
    }

    inner class MultiSelectorAdapter : ArrayAdapter<String>(context, 0, mSelections) {

        override fun getItem(position: Int): String? {
            return mSelections[position]
        }

        override fun getCount(): Int {
            return mSelections.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val viewHolder: ViewHolder
            var view: View? = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.multi_selector_content, parent, false)
                viewHolder = ViewHolder(view!!.findViewById(R.id.checkBox))
                view.tag = viewHolder
            } else {
                viewHolder = view.tag as ViewHolder
            }
            viewHolder.checkBox.text = mSelections[position]
            viewHolder.checkBox.isChecked = mSelected.containsKey(position)
            viewHolder.checkBox.tag = position
            viewHolder.checkBox.onCheckedChange { buttonView, isChecked ->
                val tag = (buttonView as CheckBox).tag as Int
                this@MultiSelectorDialog.itemCheckedListener?.invoke(mSelections[tag], isChecked)
                if (isChecked) {
                    mSelected.putIfAbsent(tag, mSelections[tag])
                } else {
                    mSelected.remove(tag)
                }
                this@MultiSelectorDialog.onSelectionsChanged?.invoke(mSelected.toSortedMap())
            }
            return view
        }

    }

    private data class ViewHolder(val checkBox: CheckBox)

}
