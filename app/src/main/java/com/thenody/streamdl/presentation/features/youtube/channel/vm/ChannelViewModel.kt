package com.thenody.streamdl.presentation.features.youtube.channel.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenody.streamdl.domain.model.ChannelData
import com.thenody.streamdl.domain.repository.YoutubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class ChannelUiState(
    val isLoading: Boolean = false,
    val data: ChannelData? = null,
    val error: String? = null
)

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val repo: YoutubeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChannelUiState(isLoading = true))
    val state: StateFlow<ChannelUiState> = _state

    private var lastUrl: String? = null

    fun load(url: String) {
        if (url == lastUrl && _state.value.data != null) return
        lastUrl = url
        _state.value = ChannelUiState(isLoading = true)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { repo.getChannel(url) }
            }
            _state.value = result.fold(
                onSuccess = { ChannelUiState(isLoading = false, data = it, error = null) },
                onFailure = { ChannelUiState(isLoading = false, data = null, error = it.message ?: "Ошибка загрузки") }
            )
        }
    }

    fun retry() {
        lastUrl?.let { load(it) }
    }
}
