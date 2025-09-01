package com.thenody.streamdl.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.navigateSingleTopTo(route: String, builder: (NavOptionsBuilder.() -> Unit)? = null) {
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
        builder?.invoke(this)
    }
}
