package com.example.myprofile.imagepreprocessing

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.myprofile.R

/**
 * Load image from string url to the ImageView using Glide
 *
 * @param src The url to the image which will be loaded to the ImageView
 */
fun ImageView.loadCircledImage(src: String) {
    GlideApp.with(this)
        .load(src)
        .circleCrop()
        .error(R.drawable.ic_person)
        .into(this)
}

fun ImageView.loadCircledImage(src: Int) {
    GlideApp.with(this)
        .load(src)
        .circleCrop()
        .error(R.drawable.ic_person)
        .into(this)
}

//fun getURLForResource(resourceId: Int): String {
//    return Uri.parse(
//        "android.resource://" + BuildConfig.APPLICATION_ID + "/" + resourceId
//    ).toString()
//}