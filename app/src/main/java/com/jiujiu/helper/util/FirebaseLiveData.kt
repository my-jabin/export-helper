package com.jiujiu.helper.util

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

// It aims to transform some firebase object to the livedata, such as Query, snapshot etc
open class FirebaseLiveData<T> : LiveData<Result<List<T>>>() {

    class NoSuchSourceException(source: Any?) : RuntimeException(
            "No such source found $source"
    )

    class Builder<B : Any>(private val clazz: Class<B>) {
        private var mSource: Any? = null
        private var mStrategy: FetchStrategy = FetchStrategy.REALTIME

        fun source(documentReference: DocumentReference): Builder<B> {
            mSource = documentReference
            return this
        }

        fun source(query: Query): Builder<B> {
            mSource = query
            return this
        }

        fun fetchStrategy(strategy: FetchStrategy): Builder<B> {
            mStrategy = strategy
            return this
        }

        fun build(): FirebaseLiveData<B> {
            return when (mSource) {
                is Query -> FirebaseQueryLiveData(mSource as Query, clazz)
                is DocumentReference -> FirebaseDocumentLiveData(mSource as DocumentReference, clazz)
                else -> {
                    return FirebaseLiveData<B>().failure(NoSuchSourceException(mSource))
                }
            }
        }
    }

    private fun failure(exception: Throwable): FirebaseLiveData<T> {
        return FirebaseLiveData<T>().apply {
            this.value = Result.failure(exception)
        }
    }

    enum class FetchStrategy {
        ONETIME, REALTIME
    }
}