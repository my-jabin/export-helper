package com.jiujiu.helper.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.jiujiu.helper.App
import com.jiujiu.helper.data.local.AppDatabase
import com.jiujiu.helper.data.local.dao.ProductDao
import com.jiujiu.helper.di.scope.DatabaseInfo
import com.jiujiu.helper.di.scope.PreferenceInfo
import com.jiujiu.helper.util.AppConstant
import com.jiujiu.helper.worker.PrePopulateDataWorker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, WorkerBindingModule::class])
class AppModule {

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstant.DATABASENAME
    }

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

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context, @DatabaseInfo databaseName: String): AppDatabase {
        // return Room.databaseBuilder(context, AppDatabase.class, databaseName).addCallback(
        // in memory database
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        OneTimeWorkRequest.Builder(PrePopulateDataWorker::class.java).build().apply {
                            WorkManager.getInstance().enqueue(this)
                        }
                    }
                }
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }


}
