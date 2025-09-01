package com.thenody.streamdl.presentation.features.grab.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenody.streamdl.domain.model.VideoInfo
import com.thenody.streamdl.domain.usecase.DownloadVideoUseCase
import com.thenody.streamdl.domain.usecase.GetVideoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrabViewModel @Inject constructor(
    private val getVideoInfo: GetVideoInfoUseCase,
    private val downloadVideo: DownloadVideoUseCase
) : ViewModel() {

    private val _videoInfo = MutableStateFlow<VideoInfo?>(null)
    val videoInfo: StateFlow<VideoInfo?> = _videoInfo

    fun fetchVideoInfo(url: String) {
        viewModelScope.launch {
            try {
                _videoInfo.value = getVideoInfo(url)
            } catch (e: Exception) {
                e.printStackTrace()
                _videoInfo.value = null
            }
        }
    }

    fun startDownload(title: String, url: String) {
        viewModelScope.launch {
            downloadVideo(title, url)
        }
    }
}
