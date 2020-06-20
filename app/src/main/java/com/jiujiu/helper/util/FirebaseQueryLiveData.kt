package com.jiujiu.helper.util


import android.os.Handler
import androidx.annotation.Nullable
import com.google.firebase.firestore.*
import org.jetbrains.anko.AnkoLogger

class FirebaseQueryLiveData<T : Any>(private val query: Query, private val clazz: Class<T>) : FirebaseLiveData<T>(), AnkoLogger {
    private val listener = MyValueEventListener()
    private var listenerRemovePending = false
    private var listenerRegistration: ListenerRegistration? = null
    private val handler = Handler()
    private val removeListener = Runnable {
        listenerRegistration!!.remove()
        listenerRemovePending = false
    }

    override fun onActive() {
        super.onActive()
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener)
        } else {
            listenerRegistration = query.addSnapshotListener(listener)
        }
        listenerRemovePending = false
    }

    override fun onInactive() {
        super.onInactive()
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000)
        listenerRemovePending = true
    }

    private inner class MyValueEventListener : EventListener<QuerySnapshot> {
        override fun onEvent(@Nullable querySnapshot: QuerySnapshot?, @Nullable e: FirebaseFirestoreException?) {
            value = if (e != null) {
                Result.failure(e)
            } else {
                val queryList = mutableListOf<T>()
                querySnapshot?.forEach { snapShot: QueryDocumentSnapshot? ->
                    queryList.add(snapShot!!.toObject(clazz))
                }
                Result.success(queryList)
            }
        }
    }
}