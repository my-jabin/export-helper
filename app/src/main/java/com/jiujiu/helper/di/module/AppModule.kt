package com.jiujiu.helper.di.module

import android.content.Context
import com.jiujiu.helper.App
import com.jiujiu.helper.di.scope.PreferenceInfo
import com.jiujiu.helper.util.AppConstant
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [FirebaseModule::class, ViewModelModule::class, WorkerBindingModule::class])
class AppModule {

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstant.PREFERENCENAME
    }

    @Provides
    @Singleton
    fun provideContext(application: App): Context {
        return application
    }

}
