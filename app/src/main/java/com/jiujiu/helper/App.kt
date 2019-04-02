package com.jiujiu.helper

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.jiujiu.helper.data.local.AppDatabase
import com.jiujiu.helper.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.util.*
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var myWorkerFactory: WorkerFactory

    @Inject
    lateinit var mDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()

        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(myWorkerFactory).build())

        setInMemoryRoomDatabases(mDatabase.openHelper.writableDatabase)
    }

    override fun applicationInjector(): AndroidInjector<out App> {
        return DaggerAppComponent.builder().create(this)
    }

    companion object {
        // for in memory database debugging
        fun setInMemoryRoomDatabases(vararg database: SupportSQLiteDatabase) {
            if (BuildConfig.DEBUG) {
                try {
                    val debugDB = Class.forName("com.amitshekhar.DebugDB")
                    val argTypes = arrayOf<Class<*>>(HashMap::class.java)
                    val inMemoryDatabases = HashMap<String, SupportSQLiteDatabase>()
                    // set your inMemory databases
                    inMemoryDatabases["InMemoryOne.db"] = database[0]
                    val setRoomInMemoryDatabase = debugDB.getMethod("setInMemoryRoomDatabases", *argTypes)
                    setRoomInMemoryDatabase.invoke(null, inMemoryDatabases)
                } catch (ignore: Exception) {

                }
            }
        }
    }
}
