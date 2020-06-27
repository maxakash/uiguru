package com.weaponoid.uiguru.model

import android.os.Parcel
import android.os.Parcelable

data class UI(
    val imageUrl: String? = null,
    val title: String? = null,
    val author: String? = null,
    val downloadUrl: String? = null,
    val fileName: String? = null,
    val source: String? = null,
    val timeStamp: Long? = 0,
    val category: String? =null,
    val tag: List<String>? = null,
    val uiImages: List<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(downloadUrl)
        parcel.writeString(fileName)
        parcel.writeString(source)
        parcel.writeStringList(tag)
        parcel.writeString(category)
        timeStamp?.let { parcel.writeLong(it) }
        parcel.writeStringList(uiImages)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UI> {
        override fun createFromParcel(parcel: Parcel): UI {
            return UI(parcel)
        }

        override fun newArray(size: Int): Array<UI?> {
            return arrayOfNulls(size)
        }
    }
}