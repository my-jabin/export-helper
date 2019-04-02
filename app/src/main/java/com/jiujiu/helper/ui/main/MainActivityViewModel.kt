package com.jiujiu.helper.ui.main

import com.jiujiu.helper.data.DataManager
import com.jiujiu.helper.ui.base.BaseViewModel

import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
        dataManager: DataManager
) : BaseViewModel(dataManager)
