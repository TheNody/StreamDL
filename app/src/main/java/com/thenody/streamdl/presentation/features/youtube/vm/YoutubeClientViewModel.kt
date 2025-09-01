package com.thenody.streamdl.presentation.features.youtube.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenody.streamdl.domain.model.YtKiosk
import com.thenody.streamdl.domain.usecase.GetKioskUseCase
import com.thenody.streamdl.domain.usecase.GetSuggestionsUseCase
import com.thenody.streamdl.domain.usecase.GetVideoInfoUseCase
import com.thenody.streamdl.domain.usecase.SearchYoutubeUseCase
import com.thenody.streamdl.presentation.features.youtube.components.YoutubeEvent
import com.thenody.streamdl.presentation.features.youtube.components.YoutubeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YoutubeClientViewModel @Inject constructor(
    private val getKiosk: GetKioskUseCase,
    private val searchYoutube: SearchYoutubeUseCase,
    private val getVideoInfo: GetVideoInfoUseCase,
    private val getSuggestions: GetSuggestionsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(YoutubeUiState())
    val state: StateFlow<YoutubeUiState> = _state

    private var suggestJob: Job? = null

    init {
        onEvent(YoutubeEvent.SelectKiosk(YtKiosk.TRENDING))
    }

    fun onEvent(e: YoutubeEvent) {
        when (e) {
            is YoutubeEvent.QueryChanged -> {
                _state.update { it.copy(searchQuery = e.value) }
                if (_state.value.isSearchActive) onEvent(YoutubeEvent.LoadSuggestions)
            }

            YoutubeEvent.EnterSearch -> _state.update {
                it.copy(
                    isSearchActive = true,
                    isSearchResults = false,
                    suggestions = emptyList()
                )
            }

            YoutubeEvent.ExitSearch -> _state.update {
                it.copy(isSearchActive = false, suggestions = emptyList())
            }

            YoutubeEvent.ClearQuery -> _state.update {
                it.copy(searchQuery = "", suggestions = emptyList())
            }

            YoutubeEvent.LoadSuggestions -> loadSuggestions()

            YoutubeEvent.Search -> {
                suggestJob?.cancel()
                _state.update { it.copy(suggestions = emptyList()) }
                searchVideos()
            }

            is YoutubeEvent.SuggestionClicked -> {
                _state.update { it.copy(searchQuery = e.value) }
                searchVideos()
            }

            is YoutubeEvent.LoadVideoInfo -> loadInfo(e.url)
            YoutubeEvent.ClearSelected    -> _state.update { it.copy(selectedVideo = null) }

            is YoutubeEvent.SelectKiosk   -> {
                _state.update {
                    it.copy(
                        selectedKiosk = e.kiosk,
                        isLoading = true,
                        error = null,
                        isSearchResults = false,
                        isSearchActive = false
                    )
                }
                viewModelScope.launch {
                    val items = runCatching { getKiosk(e.kiosk) }.getOrElse { emptyList() }
                    _state.update { it.copy(isLoading = false, items = items) }
                }
            }

            YoutubeEvent.ExitSearchResults -> {
                _state.update { it.copy(isSearchResults = false, lastSearchText = "") }
            }
        }
    }

    private fun loadSuggestions() {
        val q = _state.value.searchQuery.trim()
        suggestJob?.cancel()
        if (q.isEmpty()) {
            _state.update { it.copy(suggestions = emptyList()) }
            return
        }
        suggestJob = viewModelScope.launch {
            val list = runCatching { getSuggestions(q) }.getOrElse { emptyList() }
            _state.update { it.copy(suggestions = list) }
        }
    }

    private fun searchVideos() = viewModelScope.launch {
        val q = _state.value.searchQuery.trim()
        if (q.isEmpty()) return@launch
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
                isSearchActive = false,
                isSearchResults = true,
                lastSearchText = q,
                suggestions = emptyList()
            )
        }
        val items = runCatching { searchYoutube(q) }.getOrElse { emptyList() }
        _state.update { it.copy(isLoading = false, items = items) }
    }

    private fun loadInfo(url: String) = viewModelScope.launch {
        val info = runCatching { getVideoInfo(url) }.getOrNull()
        _state.update { it.copy(selectedVideo = info) }
    }
}
