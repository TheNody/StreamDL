package com.thenody.streamdl.core.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object Grab : Screen("grab")
    object Queue : Screen("queue")
    object Library : Screen("library")
    object Subscriptions : Screen("subscriptions")
    object Settings : Screen("settings")
    object YoutubeClient : Screen("youtube_client")
    data object Channel : Screen("channel?url={url}") {
        fun build(url: String): String = "channel?url=${Uri.encode(url)}"
    }
}
