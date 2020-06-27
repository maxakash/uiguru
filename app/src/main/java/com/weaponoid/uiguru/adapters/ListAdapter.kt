package com.weaponoid.uiguru.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.model.UI
import com.weaponoid.uiguru.util.call
import com.weaponoid.uiguru.util.loadListImage
import com.weaponoid.uiguru.views.AdobeXDDirections
import com.weaponoid.uiguru.views.FigmaDirections
//import com.weaponoid.uiguru.views.FigmaDirections
import com.weaponoid.uiguru.views.SketchDirections
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(val itemList: ArrayList<Any>, val type: String) :
    RecyclerView.Adapter<ListAdapter.uiViewHolder>(), call {

    private val MENU_ITEM_VIEW_TYPE = 0
    private val UNIFIED_NATIVE_AD_VIEW_TYPE = 1
    val loadMore by lazy { MutableLiveData<Boolean>() }


    fun updateUiList(newUiList: ArrayList<Any>) {
        itemList.clear()
        itemList.addAll(newUiList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {

        val item = itemList[position]
        if (item is UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE
        }
        return MENU_ITEM_VIEW_TYPE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): uiViewHolder {

        when (viewType) {

            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ad_unified_list, parent, false)
                return uiViewHolder(view)

            }

            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
                return uiViewHolder(view)
            }

        }
    }

    override fun onBindViewHolder(holder: uiViewHolder, position: Int) {
        val viewType = getItemViewType(position)


        if(position == itemList.size - 4 ){
            loadMore.value = true
            println("last item reached")
        }

        when (viewType) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val unifiedNativeAd = itemList.get(position) as UnifiedNativeAd
                populateUnifiedNativeAdView(unifiedNativeAd, holder.itemView as UnifiedNativeAdView)
            }
            MENU_ITEM_VIEW_TYPE -> {
                val item = itemList[position] as UI
                holder.view.uiListImage.loadListImage(item.imageUrl)
                holder.view.uiListTitle.setText(item.title)
                holder.view.uiListItem.setOnClickListener { view ->

                    when (type) {

                        "xd" -> {
                            val action = AdobeXDDirections.xdToDetail(item)
                            Navigation.findNavController(view).navigate(action)
                        }

                        "sketch" -> {
                            val action = SketchDirections.sketchToDetail(item)
                            Navigation.findNavController(view).navigate(action)
                        }

                        "figma" -> {
                            val action = FigmaDirections.figmaToDetail(item)
                            Navigation.findNavController(view).navigate(action)
                        }
                    }


                }

            }
        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }


    class uiViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }


    private fun populateUnifiedNativeAdView(
        nativeAd: UnifiedNativeAd,
        adView: UnifiedNativeAdView
    ) {

        adView.mediaView = adView.findViewById(R.id.ad_media1)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline1)
        adView.bodyView = adView.findViewById(R.id.ad_body1)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action1)
        adView.iconView = adView.findViewById(R.id.app_icon1)
        adView.priceView = adView.findViewById(R.id.ad_price1)
        adView.starRatingView = adView.findViewById(R.id.ad_stars1)
        adView.storeView = adView.findViewById(R.id.ad_store1)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser1)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView.setMediaContent(nativeAd.mediaContent)

        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.GONE
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

    override fun call(uiListItem: ArrayList<Any>) {
        itemList.addAll(uiListItem)
        notifyDataSetChanged()
    }


}