package com.weaponoid.uiguru.viewmodel

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.weaponoid.uiguru.model.FirestoreRepository
import com.weaponoid.uiguru.model.UI
import com.weaponoid.uiguru.util.infoToast
import java.util.*

class SearchResultsViewModel : ViewModel() {
    var listState: Parcelable? = null
    val uiList by lazy { MutableLiveData<ArrayList<UI>>() }
    var queryList: ArrayList<String> = arrayListOf()

    //val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    var firebaseRepository = FirestoreRepository()


    fun getSearchResults(type: String, query: String, context: Context) {
        loading.value = true
        query.toLowerCase(Locale.ENGLISH)

        val searchQuery = query.split("\\s".toRegex())
        queryList.addAll(searchQuery)

        firebaseRepository.getSearchResults(type, queryList)

            .addOnSuccessListener { result ->
                val itemList = arrayListOf<UI>()
                if (result.isEmpty) {
                    context.infoToast("No results found")
                }
                for (item in result) {
                    val uiItem = item.toObject<UI>()
                    itemList.add(uiItem)
                }
                uiList.value = itemList
                loading.value = false
                println(uiList)

            }.addOnFailureListener {
                println(it)
                loading.value = false
            }


    }


}
