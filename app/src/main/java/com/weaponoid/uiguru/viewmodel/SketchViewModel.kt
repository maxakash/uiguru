package com.weaponoid.uiguru.viewmodel

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import com.weaponoid.uiguru.adapters.ListAdapter
import com.weaponoid.uiguru.model.FirestoreRepository
import com.weaponoid.uiguru.model.UI
import com.weaponoid.uiguru.util.failedToast
import java.util.*

class SketchViewModel : ViewModel() {
    var listState: Parcelable? = null
    var unifiedAds: ArrayList<UnifiedNativeAd> = arrayListOf()
    val itemList: ArrayList<Any> = arrayListOf()
    lateinit var lazyLoadQuery: DocumentSnapshot

    //val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    var firebaseRepository = FirestoreRepository()


    fun getUIList(context: Context, listAdapter: ListAdapter) {
        loading.value = true
        itemList.clear()
        firebaseRepository.getUIList("sketch")
            .addOnSuccessListener { result ->

                lazyLoadQuery = result.documents[result.size() - 1]

                for (item in result) {
                    val uiItem = item.toObject<UI>()
                    itemList.add(uiItem)
                }

                listAdapter.updateUiList(itemList)
                loading.value = false
                // loadNativeAd(context, listAdapter)

            }.addOnFailureListener {
                println(it)
                context.failedToast("Something went wrong")
            }


    }


    fun refresh(context: Context, listAdapter: ListAdapter) {
        getUIList(context, listAdapter)
    }


    fun filter(category: String, context: Context, listAdapter: ListAdapter) {
        loading.value = true
        firebaseRepository.filterUIList(category, "sketch")
            .addOnSuccessListener { result ->
                listAdapter.itemList.clear()
                for (item in result) {
                    val uiItem = item.toObject<UI>()
                    listAdapter.itemList.add(uiItem)
                }
                loading.value = false
                // loadNativeAd(context,listAdapter)

            }.addOnFailureListener {
                println(it)
                loading.value = false
            }
    }


    fun lazyLoading(context: Context, listAdapter: ListAdapter) {
        firebaseRepository.lazyLoading("sketch", lazyLoadQuery)
            .addOnSuccessListener { result ->

                if (result.documents.size > 0) {
                    lazyLoadQuery = result.documents[result.size() - 1]
                }

                itemList.clear()

                for (item in result) {
                    val uiItem = item.toObject<UI>()
                    itemList.add(uiItem)
                }

                listAdapter.itemList.addAll(itemList)
                listAdapter.notifyDataSetChanged()
                listAdapter.loadMore.value = false
                //  loading.value = false
                //  loadNativeAd(context, listAdapter)

            }.addOnFailureListener {
                println(it)
                context.failedToast("Something went wrong")
            }
    }

    fun insertAds(listAdapter: ListAdapter) {
        if (unifiedAds.size <= 0) {
            return
        }
        val offset: Int = (itemList.size / unifiedAds.size) + 1
        var index = 7
        try {
            for (unifiedAd in unifiedAds) {
                listAdapter.itemList.add(index, unifiedAd)
                index += offset

            }
            listAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.i("error", e.toString());
        }

    }


    fun loadNativeAd(context: Context, listAdapter: ListAdapter) {
        unifiedAds.clear()
        var adLoader: AdLoader? = null

        val builder = AdLoader.Builder(
            context,
            "ca-app-pub-3940256099942544/2247696110"
        )

        adLoader = builder.forUnifiedNativeAd { unifiedNativeAd ->
            println("ad loaded")
            unifiedAds.add(unifiedNativeAd)

            if (!adLoader!!.isLoading) {
                insertAds(listAdapter)
                //listAdapter.onAdsLoaded(unifiedAds)
            }
        }.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                println("ad failed to load")
            }


        }).build()

        adLoader.loadAds(AdRequest.Builder().build(), 5)


    }


}
