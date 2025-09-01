package com.thenody.streamdl.presentation.features.youtube.components

import com.thenody.streamdl.domain.model.VideoInfo
import com.thenody.streamdl.domain.model.VideoListItem
import com.thenody.streamdl.domain.model.YtKiosk

data class YoutubeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<VideoListItem> = emptyList(),
    val searchQuery: String = "",
    val selectedVideo: VideoInfo? = null,
    val selectedKiosk: YtKiosk = YtKiosk.TRENDING,

    val isSearchActive: Boolean = false,
    val isSearchResults: Boolean = false,
    val lastSearchText: String = "",
    val suggestions: List<String> = emptyList()
)
