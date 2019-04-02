package com.jiujiu.helper.di.builder

import com.jiujiu.helper.di.module.MainActivityModule
import com.jiujiu.helper.ui.main.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindsMainActivity(): MainActivity

}
