package com.jiujiu.helper.di.builder

import com.jiujiu.helper.di.module.LoginActivityModule
import com.jiujiu.helper.di.module.MainActivityModule
import com.jiujiu.helper.ui.login.LoginActivity
import com.jiujiu.helper.ui.main.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindsMainActivity(): MainActivity


    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun bindsLoginActivity(): LoginActivity


}
