package com.weaponoid.uiguru.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.RequestConfiguration
import com.weaponoid.uiguru.adapters.SliderAdapter
import com.weaponoid.uiguru.model.Download
import com.weaponoid.uiguru.util.infoToast
import java.util.*


class DetailViewModel : ViewModel() {


    fun shareLink(link: String, activity: FragmentActivity) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        activity.startActivity(Intent.createChooser(shareIntent, "Share link via"))

    }

    fun download(url: String, filename: String, context: Context) {
        context.infoToast("File downloading in background")
        Download(url, filename, context).downloadFile()
    }

    fun loadNativeAd(context: Context, sliderAdapter: SliderAdapter) {
        RequestConfiguration.Builder().setTestDeviceIds(
            Arrays.asList("2C9C8CEEF36F65BCD47F1D0DA71B7F53")
        )


        val builder = AdLoader.Builder(
            context,
            "ca-app-pub-3940256099942544/2247696110"
        )//ca-app-pub-2183953773716378/9427661940 //ca-app-pub-3940256099942544/2247696110
        builder.forUnifiedNativeAd { unifiedNativeAd ->
            sliderAdapter.updateList(unifiedNativeAd)
        }


        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                println("ad failed to load")
            }


        }).build()

        adLoader.loadAds(AdRequest.Builder().build(), 1)


    }

    fun downloadLink(url: String, activity: FragmentActivity) {
        val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(Intent.createChooser(shareIntent, "Open link via"))
    }


}
