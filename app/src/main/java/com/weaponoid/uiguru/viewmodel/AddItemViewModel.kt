package com.weaponoid.uiguru.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.weaponoid.uiguru.model.FirestoreRepository
import com.weaponoid.uiguru.model.UI

class AddItemViewModel : ViewModel() {
    private var firebaseRepository = FirestoreRepository()


    fun saveItem(
        type: String,
        context: Context,
        title: String,
        imageUrl: String,
        downloadUrl: String,
        author: String,
        source: String,
        tag: String,
        uiImages: String,
        fileName: String,
        category: String
    ) {

        val timeStamp = System.currentTimeMillis()
        val tagArray = tag.split(",")
        val imageList = uiImages.split(",")


        val item = UI(
            imageUrl = imageUrl,
            title = title,
            downloadUrl = downloadUrl,
            author = author,
            source = source,
            timeStamp = timeStamp,
            tag = tagArray,
            uiImages = imageList,
            fileName = fileName,
            category = category
        )
        firebaseRepository.saveItem(item, type, context)

    }

}
