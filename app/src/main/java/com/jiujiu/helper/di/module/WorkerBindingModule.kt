package com.jiujiu.helper.di.module

import androidx.work.WorkerFactory

import com.jiujiu.helper.di.MyWorkerFactory
import com.jiujiu.helper.di.scope.WorkerKey
import com.jiujiu.helper.worker.CustomWorkerFactory
import com.jiujiu.helper.worker.PrePopulateDataWorker

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerBindingModule {

    @Binds
    @IntoMap
    @WorkerKey(PrePopulateDataWorker::class)
    abstract fun bindsPrePopulateDataWorker(factory: PrePopulateDataWorker.Factory): CustomWorkerFactory

    @Binds
    abstract fun bindsWorkerFactory(factory: MyWorkerFactory): WorkerFactory
}
