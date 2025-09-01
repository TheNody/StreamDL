package com.thenody.streamdl.core.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thenody.streamdl.presentation.features.grab.ui.GrabScreen
import com.thenody.streamdl.presentation.features.library.ui.LibraryScreen
import com.thenody.streamdl.presentation.features.queue.ui.QueueScreen
import com.thenody.streamdl.presentation.features.settings.ui.SettingsScreen
import com.thenody.streamdl.presentation.features.subs.ui.SubscriptionsScreen
import com.thenody.streamdl.presentation.features.youtube.channel.ui.ChannelScreen
import com.thenody.streamdl.presentation.features.youtube.ui.YoutubeClientScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Grab.route
    ) {
        composable(Screen.Grab.route) {
            GrabScreen(
                navController = navController,
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
        composable(Screen.Queue.route) {
            QueueScreen(
                navController = navController,
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
        composable(Screen.Library.route) {
            LibraryScreen(
                navController = navController,
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
        composable(Screen.Subscriptions.route) {
            SubscriptionsScreen(
                navController = navController,
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                navController = navController,
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
        composable(Screen.YoutubeClient.route) {
            YoutubeClientScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.Channel.route,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("url") ?: return@composable
            val url = Uri.decode(encoded)
            ChannelScreen(navController = navController, channelUrl = url)
        }
    }
}
