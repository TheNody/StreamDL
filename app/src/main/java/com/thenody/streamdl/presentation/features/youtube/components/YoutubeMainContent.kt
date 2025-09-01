package com.thenody.streamdl.presentation.features.youtube.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thenody.streamdl.R
import com.thenody.streamdl.domain.model.VideoListItem
import com.thenody.streamdl.domain.model.YtKiosk

@Composable
fun YoutubeMainContent(
    padding: PaddingValues,
    ui: YoutubeUiState,
    onVideoClick: (VideoListItem) -> Unit,
    onChannelClick: (String) -> Unit,
    onSelectKiosk: (YtKiosk) -> Unit
) {
    val kiosks = listOf(
        YtKiosk.TRENDING to R.drawable.ic_trending,
        YtKiosk.MUSIC to R.drawable.ic_music,
        YtKiosk.GAMING to R.drawable.ic_gaming,
        YtKiosk.LIVE to R.drawable.ic_live
    )
    val selectedIndex = kiosks.indexOfFirst { it.first == ui.selectedKiosk }.coerceAtLeast(0)

    Column(Modifier.fillMaxSize().padding(padding)) {
        if (!ui.isSearchResults) {
            TabRow(selectedTabIndex = selectedIndex, modifier = Modifier.fillMaxWidth()) {
                kiosks.forEachIndexed { index, (kiosk, iconRes) ->
                    Tab(
                        selected = index == selectedIndex,
                        onClick = { onSelectKiosk(kiosk) },
                        icon = { Icon(painterResource(iconRes), contentDescription = kiosk.title) }
                    )
                }
            }
            HorizontalDivider()
        }

        when {
            ui.isLoading -> Box(Modifier.fillMaxSize().padding(24.dp), Alignment.Center) {
                CircularProgressIndicator()
            }
            ui.items.isEmpty() -> Box(Modifier.fillMaxSize().padding(24.dp), Alignment.Center) {
                Text(
                    ui.error ?: "Нет данных",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ui.items, key = { it.url }) { video ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        VideoCardYt(
                            item = video,
                            onClick = { onVideoClick(video) },
                            onChannelClick = onChannelClick
                        )
                    }
                }
            }
        }
    }
}

