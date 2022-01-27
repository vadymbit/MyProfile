package com.vadym.myprofile.app.utils.ext

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigation(directions: NavDirections) {
    currentDestination?.getAction(directions.actionId)?.run { navigate(directions) }
}