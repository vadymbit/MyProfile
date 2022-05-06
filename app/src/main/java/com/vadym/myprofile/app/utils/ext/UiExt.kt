package com.vadym.myprofile.app.utils.ext

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showSnackbar(
    msg: String,
    binding: ViewBinding,
    actionName: String = "",
    action: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, binding.root, msg, Snackbar.LENGTH_SHORT)
    action?.let {
        snackbar.setAction(actionName) {
            action()
        }
    }
    snackbar.show()
}

fun Activity.hideKeyboard() {
    WindowInsetsControllerCompat(
        window,
        window.decorView
    ).hide(WindowInsetsCompat.Type.ime())
}