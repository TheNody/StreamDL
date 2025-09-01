package com.thenody.streamdl.presentation.features.youtube.components

import com.thenody.streamdl.domain.model.YtKiosk

sealed interface YoutubeEvent {
    data class QueryChanged(val value: String) : YoutubeEvent
    data object Search : YoutubeEvent
    data class SuggestionClicked(val value: String) : YoutubeEvent
    data class LoadVideoInfo(val url: String) : YoutubeEvent
    data object ClearSelected : YoutubeEvent
    data class SelectKiosk(val kiosk: YtKiosk) : YoutubeEvent
    data object EnterSearch : YoutubeEvent
    data object ExitSearch : YoutubeEvent
    data object ClearQuery : YoutubeEvent
    data object LoadSuggestions : YoutubeEvent
    data object ExitSearchResults : YoutubeEvent
}
