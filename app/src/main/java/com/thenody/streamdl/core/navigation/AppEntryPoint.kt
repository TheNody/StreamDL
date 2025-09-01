package com.thenody.streamdl.core.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val backStack by navController.currentBackStackEntryAsState()
    LaunchedEffect(backStack) {
        when (backStack?.destination?.route) {
            Screen.Grab.route          -> selectedTabIndex = 0
            Screen.Queue.route         -> selectedTabIndex = 1
            Screen.Library.route       -> selectedTabIndex = 2
            Screen.Subscriptions.route -> selectedTabIndex = 3
            Screen.Settings.route      -> selectedTabIndex = 4
        }
    }

    AppNavigation(
        navController = navController,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { index ->
            selectedTabIndex = index
            when (index) {
                0 -> navController.navigateSingleTopTo(Screen.Grab.route)
                1 -> navController.navigateSingleTopTo(Screen.Queue.route)
                2 -> navController.navigateSingleTopTo(Screen.Library.route)
                3 -> navController.navigateSingleTopTo(Screen.Subscriptions.route)
                4 -> navController.navigateSingleTopTo(Screen.Settings.route)
            }
        }
    )
}
