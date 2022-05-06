package com.vadym.myprofile.app.utils

import android.content.Context
import com.vadym.myprofile.R
import javax.inject.Inject

class NetworkErrorParser @Inject constructor(private val context: Context) {
    fun parseErrorMessage(errorCode: Int): String {
        return when (errorCode) {
            -1 -> context.getString(R.string.error_no_internet)
            0 -> context.getString(R.string.error_try_again)
            204 -> context.getString(R.string.error_bad_server_response)
            400 -> context.getString(R.string.error_user_exist)
            401 -> context.getString(R.string.error_authorization)
            403 -> context.getString(R.string.error_invalid_credentials)
            404 -> context.getString(R.string.error_user_not_found)
            else -> context.getString(R.string.error_unknown)
        }
    }
}