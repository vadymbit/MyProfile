package com.vadym.myprofile.app.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

object AppIntentHelper {
    private fun isAppAvailable(context: Context, appName: String?): Boolean {
        val pm: PackageManager = context.packageManager
        return try {
            pm.getPackageInfo(appName!!, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAppIntent(
        context: Context,
        appPackage: String,
        appUri: String,
        browserUri: String
    ): Intent {
        return if (isAppAvailable(context, appPackage)) {
            Intent(Intent.ACTION_VIEW).apply {
                `package` = appPackage
                data = Uri.parse(appUri)
            }
        } else {
            Intent(Intent.ACTION_VIEW, Uri.parse(browserUri))
        }
    }
}