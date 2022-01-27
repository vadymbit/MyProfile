package com.vadym.myprofile.app.utils.imagepreprocessing

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vadym.myprofile.R

/**
 * Load image from string url to the ImageView using Glide
 *
 * @param src The url to the image which will be loaded to the ImageView
 */
fun ImageView.loadCircledImage(src: String?) {
    Glide.with(this)
        .load(src)
        .circleCrop()
        .error(R.drawable.ic_person)
        .into(this)
}

fun ImageView.loadCircledImage(src: Int) {
    Glide.with(this)
        .load(src)
        .circleCrop()
        .error(R.drawable.ic_person)
        .into(this)
}