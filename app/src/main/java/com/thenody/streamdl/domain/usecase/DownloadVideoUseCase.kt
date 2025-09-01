package com.thenody.streamdl.domain.usecase

import com.thenody.streamdl.domain.repository.VideoRepository
import javax.inject.Inject

class DownloadVideoUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(title: String, url: String) {
        repository.downloadVideo(title, url)
    }
}
