package com.jiujiu.helper.ui.base

import androidx.lifecycle.ViewModel

import com.jiujiu.helper.data.DataManager

import org.jetbrains.anko.AnkoLogger

open class BaseViewModel(protected val dataManager: DataManager) : ViewModel(), AnkoLogger {
}