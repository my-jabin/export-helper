package com.jiujiu.helper.util


import android.os.Handler
import com.google.firebase.firestore.*
import org.jetbrains.anko.AnkoLogger

class FirebaseDocumentLiveData<T : Any>(
        private val documentReference: DocumentReference,
        private val clazz: Class<T>
) : FirebaseLiveData<T>(), AnkoLogger {
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
            // The snapshot listener will receive a new query snapshot every time the query results change
            // (that is, when a document is added, removed, or modified).
            listenerRegistration = documentReference.addSnapshotListener(listener)
        }
        listenerRemovePending = false
    }

    override fun onInactive() {
        super.onInactive()
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000)
        listenerRemovePending = true
    }

    private inner class MyValueEventListener : EventListener<DocumentSnapshot> {
        override fun onEvent(documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
            value = if (e != null) {
                Result.failure(e)
            } else {
                val queryList = mutableListOf<T>()
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    queryList.add(documentSnapshot.toObject(clazz)!!)
                }
                Result.success(queryList)
            }
        }
    }
}