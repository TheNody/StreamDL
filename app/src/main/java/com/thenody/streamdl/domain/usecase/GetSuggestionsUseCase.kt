package com.thenody.streamdl.domain.usecase

import com.thenody.streamdl.domain.repository.YoutubeRepository
import javax.inject.Inject

class GetSuggestionsUseCase @Inject constructor(
    private val repo: YoutubeRepository
) {
    suspend operator fun invoke(query: String): List<String> =
        repo.getSuggestions(query)
}