package com.weaponoid.uiguru.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.util.getProgressDrawable
import com.weaponoid.uiguru.util.loadSliderImage
import kotlinx.android.synthetic.main.slider_image.view.*



class SliderAdapter(var context: Context, var itemList: ArrayList<Any>) :
    RecyclerView.Adapter<SliderAdapter.itemViewHolder>() {

    private val MENU_ITEM_VIEW_TYPE = 0
    private val UNIFIED_NATIVE_AD_VIEW_TYPE = 1


    fun updateList(item: UnifiedNativeAd){
        var postion: Int = itemList.size
         postion = (postion/2) + 1
        itemList.add(postion,item)
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {

        val item = itemList[position]
        if (item is UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE
        }
        return MENU_ITEM_VIEW_TYPE
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        when (viewType) {

            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.ad_unified, parent, false)
                return itemViewHolder(view)

            }

            else -> {
                val inflater =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.slider_image, parent, false)
                return itemViewHolder(inflater)
            }

        }

    }


    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val viewType = getItemViewType(position)

        when (viewType) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val unifiedNativeAd = itemList.get(position) as UnifiedNativeAd
                populateUnifiedNativeAdView(unifiedNativeAd, holder.itemView as UnifiedNativeAdView)
            }
            MENU_ITEM_VIEW_TYPE -> {

                holder.itemView.slider_image.loadSliderImage(
                    itemList[position] as String,
                    getProgressDrawable(context)
                )

            }
        }

    }


    override fun getItemCount() = itemList.size


    class itemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


    private fun populateUnifiedNativeAdView(
        nativeAd: UnifiedNativeAd,
        adView: UnifiedNativeAdView
    ) {

        adView.mediaView = adView.findViewById(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView.setMediaContent(nativeAd.mediaContent)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon.drawable
            )
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }


        adView.setNativeAd(nativeAd)


    }



}

