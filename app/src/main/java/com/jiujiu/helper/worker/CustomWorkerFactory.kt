package com.jiujiu.helper.worker

import android.content.Context

import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface CustomWorkerFactory {
    fun create(context: Context, workerParams: WorkerParameters): ListenableWorker
}
