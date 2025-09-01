package com.thenody.streamdl.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thenody.streamdl.presentation.components.BottomBar

@Composable
fun AppScaffold(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding()
            ) {
                content()
            }

            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                BottomBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = onTabSelected
                )
            }
        }
    }
}
