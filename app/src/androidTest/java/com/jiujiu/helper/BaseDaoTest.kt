package com.jiujiu.helper

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jiujiu.helper.data.local.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseDaoTest {
    protected lateinit var db: AppDatabase
    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java).build()
        generateDaoInstance()
    }

    abstract fun generateDaoInstance()

    @After
    fun closeDb() {
        db.close()
    }
}