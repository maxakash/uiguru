package com.weaponoid.uiguru.util

import android.icu.text.LocaleDisplayNames
import android.view.View




interface UIDetailListener {

    fun onClick(view: View)
}

interface call{
    fun call(uiListItem: ArrayList<Any>)
}


