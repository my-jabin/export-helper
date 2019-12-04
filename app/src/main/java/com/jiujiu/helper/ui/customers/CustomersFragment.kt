package com.jiujiu.helper.ui.customers

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.databinding.FragmentCustomersBinding
import com.jiujiu.helper.ui.base.BaseFragment
import com.jiujiu.helper.ui.main.MainFragmentDirections
import com.jiujiu.helper.ui.widget.SpaceItemDecoration
import com.jiujiu.helper.util.toPinyin
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter


class CustomersFragment : BaseFragment<FragmentCustomersBinding, CustomersFragViewModel>() {

    override val bindingVariableId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_customers

    override val viewModelType: Class<CustomersFragViewModel>
        get() = CustomersFragViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.rvCustomer.addItemDecoration(SpaceItemDecoration(12, 12, 12, 12))
        val adapter = SectionedRecyclerViewAdapter()
        binding.rvCustomer.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel.customerLiveData.observe(this, Observer {
            val adapter = binding.rvCustomer.adapter as? SectionedRecyclerViewAdapter
            adapter?.removeAllSections()
            val groupedCustomer = it.groupBy { c -> c.name?.get(0)?.toPinyin()?.get(0) }.toSortedMap(compareBy<Char?> { c -> c })
            groupedCustomer.entries.forEach { entry ->
                adapter?.addSection(CustomerSection(entry.key, entry.value, onDeleteCustomer))
            }
            adapter?.notifyDataSetChanged()
        })
    }

    private val onDeleteCustomer = { c: Customer -> viewModel.deleteCustomer(c) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                val action = MainFragmentDirections.actionMainFragmentToAddEditCustomerFragment(-1)
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


//    private fun getCustomerWithLetter(letter: Char, customers: List<Customer>): List<String> {
//        val contacts = ArrayList()
//
//        for (contact in resources.getStringArray(R.array.names)) {
//            if (contact[0] == letter) {
//                contacts.add(contact)
//            }
//        }
//
//        return contacts
//    }

    companion object {
        fun getInstance(): CustomersFragment = CustomersFragment()
    }

}
