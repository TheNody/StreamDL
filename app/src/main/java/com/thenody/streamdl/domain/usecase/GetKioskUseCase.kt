package com.thenody.streamdl.domain.usecase

import com.thenody.streamdl.domain.model.YtKiosk
import com.thenody.streamdl.domain.repository.YoutubeRepository
import javax.inject.Inject

class GetKioskUseCase @Inject constructor(
    private val repo: YoutubeRepository
) {
    suspend operator fun invoke(kiosk: YtKiosk) = repo.getKiosk(kiosk)
}
