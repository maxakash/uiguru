package com.weaponoid.uiguru.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.weaponoid.uiguru.R
import es.dmoral.toasty.Toasty


fun ImageView.loadListImage(uri: String?) {
    val options = RequestOptions()

    //.fitCenter()
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)

}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 40f
        start()
    }

}

fun ImageView.loadSliderImage(uri: String?, progressDrawable: CircularProgressDrawable) {

    progressDrawable.setColorSchemeColors(context.getColor(R.color.colorAccent))
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .fitCenter()
    // .error(R.mipmap.ic_launcher_round)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)

}

fun Context.infoToast(message: CharSequence) {

    Toasty.info(applicationContext, message, Toast.LENGTH_LONG, true).show()

}

fun Context.successToast(message: CharSequence) {

    Toasty.success(applicationContext, message, Toast.LENGTH_LONG, true).show()

}

fun Context.failedToast(message: CharSequence) {


    Toasty.error(applicationContext, message, Toast.LENGTH_LONG, true).show()

}


@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadListImage(url)
}

