package com.jiujiu.helper.ui.customers

import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jiujiu.helper.R
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.ui.main.MainFragmentDirections
import com.jiujiu.lib.CircleTextView
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection


class CustomerSection(val title: Char?, val customers: List<Customer>, val callback: (Customer) -> Unit) : StatelessSection(
        SectionParameters.builder()
                .itemResourceId(R.layout.customer_item)
                .headerResourceId(R.layout.customers_section_header)
                .build()
) {

    companion object {
        const val DURATION: Long = 200
    }

    override fun getContentItemsTotal(): Int {
        return customers.size
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        return ItemViewHolder(view!!)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemViewHolder = holder as? ItemViewHolder
        var customerId: String?
        customers[position].let {
            customerId = it.id
            itemViewHolder?.circleText?.setText(it.name ?: "")
            itemViewHolder?.customerName?.text = it.name

            if (!it.validAddress()) {
                itemViewHolder?.customerAddress?.visibility = View.GONE
            } else {
                itemViewHolder?.customerAddress?.text = it.address
            }

            if (it.IDNumber == null) {
                itemViewHolder?.idNumber?.visibility = View.GONE
            } else {
                itemViewHolder?.idNumber?.text = it.IDNumber
            }

            if (it.phoneNumber == null) {
                itemViewHolder?.telephone?.visibility = View.GONE
            } else {
                itemViewHolder?.telephone?.text = it.phoneNumber
            }

            if (it.email == null) {
                itemViewHolder?.email?.visibility = View.GONE
            } else {
                itemViewHolder?.email?.text = it.email
            }
        }

        itemViewHolder?.popupMenu?.apply {
            setOnMenuItemClickListener { item ->
                run {
                    when (item.itemId) {
                        R.id.menu_customer_edit -> {
                            val action = MainFragmentDirections.actionMainFragmentToAddEditCustomerFragment(customerId!!)
                            itemViewHolder.view.findNavController().navigate(action)
                        }
                        R.id.menu_customer_delete -> callback.invoke(customers[position])
                    }
                }
                true
            }
        }

        itemViewHolder?.expandableIcon?.setOnClickListener {
            itemViewHolder.popupMenu.show()
        }

//        itemViewHolder?.headerLayout?.setOnClickListener {
//            itemViewHolder.run {
//                if (this.contentLayout.visibility == View.GONE) {
//                    this.expandableIcon.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp)
//                    expand(this.contentLayout)
//                } else {
//                    this.expandableIcon.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp)
//                    collapse(this.contentLayout)
//                }
//            }
//        }
    }

//    private fun expand(v: View) {
//        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
//        val targetHeight = v.measuredHeight
//        v.layoutParams.height = 0
//        v.visibility = View.VISIBLE
//
//        val animation = object : Animation() {
//            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
//                v.layoutParams.height = if (interpolatedTime == 1f) LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
//                v.requestLayout()
//            }
//
//            override fun willChangeBounds(): Boolean {
//                return true
//            }
//        }
//        animation.duration = DURATION
//        animation.interpolator = AccelerateDecelerateInterpolator()
//        v.startAnimation(animation)
//    }
//
//    private fun collapse(v: View) {
//        val initialHeight = v.measuredHeight
//        val animation = object : Animation() {
//            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
//                if (interpolatedTime == 1f) {
//                    v.visibility = View.GONE
//                } else {
//                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
//                    v.requestLayout()
//                }
//            }
//
//            override fun willChangeBounds(): Boolean {
//                return true
//            }
//
//            override fun willChangeTransformationMatrix(): Boolean {
//                return true
//            }
//        }
//
//        animation.duration = DURATION
//        animation.interpolator = AccelerateDecelerateInterpolator()
//        v.startAnimation(animation)
//    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        return HeaderViewHolder(view!!)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val headerViewHolder = holder as? HeaderViewHolder
        headerViewHolder?.header?.text = title.toString()
    }

    class ItemViewHolder internal constructor(internal val view: View) : RecyclerView.ViewHolder(view) {
        val circleText: CircleTextView = view.findViewById(R.id.customer_circle_text)
        val customerName: TextView = view.findViewById(R.id.tv_customer_name)
        val customerAddress: TextView = view.findViewById(R.id.tv_customer_address)
        val idNumber: TextView = view.findViewById(R.id.tv_customer_ID_number)
        val telephone: TextView = view.findViewById(R.id.tv_customer_telephone)
        val email: TextView = view.findViewById(R.id.tv_customer_email)
        val expandableIcon: ImageView = view.findViewById(R.id.ic_expandable)

        val popupMenu = PopupMenu(view.context, expandableIcon).apply {
            try {
                // Reflection apis to enforce show icon
                val fields = this.javaClass.declaredFields
                for (field in fields) {
                    if (field.name == "mPopup") {
                        field.isAccessible = true
                        val menuPopupHelper = field.get(this)
                        val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons = classPopupHelper.getMethod("setForceShowIcon", Boolean::class.javaPrimitiveType)
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            this.menuInflater.inflate(R.menu.customer_item_operator, this.menu)
        }
    }

    class HeaderViewHolder internal constructor(internal val view: View) : RecyclerView.ViewHolder(view) {
        val header: TextView = view.findViewById(R.id.tv_header)
    }
}