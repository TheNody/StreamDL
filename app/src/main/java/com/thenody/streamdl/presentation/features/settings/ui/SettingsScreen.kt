package com.thenody.streamdl.presentation.features.settings.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.thenody.streamdl.core.navigation.AppScaffold

@Composable
fun SettingsScreen(
    navController: NavHostController,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    AppScaffold(
        selectedIndex = selectedIndex,
        onTabSelected = onTabSelected
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Settings Screen",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
