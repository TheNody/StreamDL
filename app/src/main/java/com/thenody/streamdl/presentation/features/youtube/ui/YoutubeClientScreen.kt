package com.thenody.streamdl.presentation.features.youtube.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.thenody.streamdl.core.navigation.Screen
import com.thenody.streamdl.core.util.extension.ensureYoutubeAbsolute
import com.thenody.streamdl.presentation.features.grab.ui.components.DownloadDialog
import com.thenody.streamdl.presentation.features.youtube.components.SearchSuggestionsPage
import com.thenody.streamdl.presentation.features.youtube.components.YoutubeEvent
import com.thenody.streamdl.presentation.features.youtube.components.YoutubeMainContent
import com.thenody.streamdl.presentation.features.youtube.components.YoutubeTopBar
import com.thenody.streamdl.presentation.features.youtube.vm.YoutubeClientViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubeClientScreen(
    navController: NavHostController,
    viewModel: YoutubeClientViewModel = hiltViewModel()
) {
    val ui by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            YoutubeTopBar(
                ui = ui,
                onBackToGrab   = { navController.navigate(Screen.Grab.route) },
                onEnterSearch  = { viewModel.onEvent(YoutubeEvent.EnterSearch) },
                onExitSearch   = { viewModel.onEvent(YoutubeEvent.ExitSearch) },
                onQueryChange  = { viewModel.onEvent(YoutubeEvent.QueryChanged(it)) },
                onSubmitSearch = { viewModel.onEvent(YoutubeEvent.Search) },
                onClear        = { viewModel.onEvent(YoutubeEvent.ClearQuery) },
                onExitResults  = { viewModel.onEvent(YoutubeEvent.ExitSearchResults) },
                onEditTitle    = {
                    viewModel.onEvent(YoutubeEvent.EnterSearch)
                    viewModel.onEvent(YoutubeEvent.QueryChanged(ui.lastSearchText))
                }
            )
        }
    ) { padding ->
        if (ui.isSearchActive) {
            SearchSuggestionsPage(
                padding = padding,
                suggestions = ui.suggestions,
                onSuggestionClick = { viewModel.onEvent(YoutubeEvent.SuggestionClicked(it)) }
            )
        } else {
            YoutubeMainContent(
                padding = padding,
                ui = ui,
                onVideoClick = { video ->
                    viewModel.onEvent(YoutubeEvent.LoadVideoInfo(video.url))
                    showDialog = true
                },
                onChannelClick = { channelUrl ->
                    val absolute = channelUrl.ensureYoutubeAbsolute()
                    navController.navigate(Screen.Channel.build(absolute))
                },
                onSelectKiosk = { k -> viewModel.onEvent(YoutubeEvent.SelectKiosk(k)) }
            )
        }
    }

    ui.selectedVideo?.let { info ->
        if (showDialog) {
            DownloadDialog(
                videoInfo = info,
                onDismiss = {
                    showDialog = false
                    viewModel.onEvent(YoutubeEvent.ClearSelected)
                },
                onDownload = {
                    showDialog = false
                    viewModel.onEvent(YoutubeEvent.ClearSelected)
                }
            )
        }
    }
}
