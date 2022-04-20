package com.vadym.myprofile.app.utils.ext

import android.view.View

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}