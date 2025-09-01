package com.thenody.streamdl.domain.usecase

import com.thenody.streamdl.domain.repository.YoutubeRepository
import javax.inject.Inject

class SearchYoutubeUseCase @Inject constructor(
    private val repo: YoutubeRepository
) {
    suspend operator fun invoke(query: String) = repo.search(query)
}