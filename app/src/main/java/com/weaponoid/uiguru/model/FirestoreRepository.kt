package com.weaponoid.uiguru.model

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.weaponoid.uiguru.util.successToast

class FirestoreRepository() {

    private var firestore = FirebaseFirestore.getInstance()


    fun getUIList(type: String): Task<QuerySnapshot> {

        val uiList = firestore.collection(type)
            .limit(50)
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .get()
        return uiList
    }

    fun filterUIList(category: String, type: String): Task<QuerySnapshot> {
        val uiList: Task<QuerySnapshot>
        if (category.equals("All")) {
            uiList = firestore.collection(type)
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .limit(50)
                .get()
        } else {
            uiList = firestore.collection(type)
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .whereEqualTo("category", category)
                .limit(50)
                .get()
        }

        return uiList

    }


    fun lazyLoading(type: String,startAt:DocumentSnapshot): Task<QuerySnapshot> {

        println("this method called")

        val uiList = firestore.collection(type)

            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .startAfter(startAt)
            .limit(50)
            .get()
        return uiList
    }


    fun getSearchResults(type: String, query: ArrayList<String>): Task<QuerySnapshot> {
        println(query)
        val uiList = firestore.collection(type)
           // .orderBy("timeStamp", Query.Direction.DESCENDING)

            .whereArrayContainsAny("tag", query)
            .limit(50)
            .get()
        return uiList
    }

    fun saveItem(ui: UI, type: String, context: Context) {
        firestore.collection(type).add(ui).addOnSuccessListener {
            context.successToast("Item saved")
        }.addOnFailureListener { e ->
            println(e)
        }

    }

}