package com.jiujiu.helper.ui.customers.addEdit

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jiujiu.helper.BR
import com.jiujiu.helper.R
import com.jiujiu.helper.data.model.Customer
import com.jiujiu.helper.databinding.AddEditCustomerBinding
import com.jiujiu.helper.ui.base.BaseFragment
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citypickerview.CityPickerView
import kotlinx.android.synthetic.main.add_edit_customer.*
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk27.coroutines.onClick

class AddEditCustomerFragment : BaseFragment<AddEditCustomerBinding, AddEditCustomerViewModel>() {
    override val bindingVariableId: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.add_edit_customer
    override val viewModelType: Class<AddEditCustomerViewModel>
        get() = AddEditCustomerViewModel::class.java

    private val mPicker = CityPickerView()
    private val cityConfig = CityConfig.Builder().build()

    private val args: AddEditCustomerFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPicker.init(this.context)
        cityConfig.apply {
            defaultProvinceName = getString(R.string.text_fujian)
            defaultCityName = getString(R.string.text_xiamen)
            defaultDistrict = getString(R.string.text_simingqu)
        }
        mPicker.setConfig(cityConfig)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        viewModel.loadCustomerById(args.customerId)
    }

    private fun setupView() {
        mPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                button_province.text = province?.name
                button_city.text = city?.name
                button_district.text = district?.name
            }
        })
        layout_customer_address.onClick { selectCity() }
        button_province.onClick { selectCity() }
        button_city.onClick { selectCity() }
        button_district.onClick { selectCity() }

        button_save_customer.onClick {
            if (isInputDataValid())
                viewModel.saveCustomer(binding.customer)
        }

        et_customer_name.requestFocus()
    }

    private fun isInputDataValid(): Boolean {
        var result = true
        if (TextUtils.isEmpty(et_customer_name.text)) {
            et_customer_name.error = getString(R.string.error_empty_name)
            et_customer_name.requestFocus()
            result = false
        }

        if (!TextUtils.isEmpty(et_customer_id_card.text) && et_customer_id_card.text?.length != 18) {
            et_customer_id_card.error = getString(R.string.error_id_number)
            et_customer_id_card.requestFocus()
            result = false
        }
        return result
    }

    private fun selectCity() {
        hideKeyboard()
        mPicker.showCityPicker()
    }

    private fun setupViewModel() {
        viewModel.customerLiveData.observe(this.viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                binding.customer = it.getOrNull()?.get(0) ?: Customer().apply {
                    street = null
                    province = cityConfig.defaultProvinceName
                    city = cityConfig.defaultCityName
                    district = cityConfig.defaultDistrict
                }
            } else {
                info {
                    it.exceptionOrNull()
                }
            }
        })

        viewModel.navigateUpEvent.observe(this.viewLifecycleOwner, Observer {
            findNavController().navigateUp()
        })
    }
}