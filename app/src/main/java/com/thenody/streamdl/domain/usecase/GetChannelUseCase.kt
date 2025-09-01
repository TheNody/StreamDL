package com.thenody.streamdl.domain.usecase

import com.thenody.streamdl.domain.repository.YoutubeRepository
import javax.inject.Inject

class GetChannelUseCase @Inject constructor(
    private val repo: YoutubeRepository
) {
    suspend operator fun invoke(url: String) = repo.getChannel(url)
}
