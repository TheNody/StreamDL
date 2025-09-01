package com.thenody.streamdl.domain.usecase

import com.thenody.streamdl.domain.model.VideoInfo
import com.thenody.streamdl.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideoInfoUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(url: String): VideoInfo {
        return repository.getVideoInfo(url)
    }
}